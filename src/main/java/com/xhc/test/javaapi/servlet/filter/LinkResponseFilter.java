package com.xhc.test.javaapi.servlet.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter(
    filterName="linkResponseFilter1",
    servletNames="testResponseServlet",
    initParams={@WebInitParam(name="Oracle", value="https://www.oracle.com"),
            @WebInitParam(name="Mysql", value="https://www.mysql.com/"),
            @WebInitParam(name="Redis", value="https://redis.io/"),
            @WebInitParam(name="Mongodb", value="https://www.mongodb.com/")}
)
public class LinkResponseFilter implements Filter {

    FilterConfig filterconfig;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterconfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        WrappedResponse wrappedResponse = new WrappedResponse((HttpServletResponse)response);
        chain.doFilter(request, wrappedResponse);
        wrappedResponse.close();
    }

    @Override
    public void destroy() {

    }

    
    class WrappedResponse extends HttpServletResponseWrapper {
        boolean linkText;
        PrintWriter client;
        
        public WrappedResponse(HttpServletResponse res) {
            super(res);
        }
        
        public void setContentType(String mime) {
            super.setContentType(mime);
            if(mime.startsWith("text/html")){
                linkText = true;
            }
        }
        
        public PrintWriter getWriter() throws IOException {
            if(client == null) {
                if(linkText) {
                    client = new LinkWriter(super.getWriter(), new ByteArrayOutputStream());
                }else {
                    client = super.getWriter();
                }
            }
            return client;
        }
        
        void close() {
            if(client != null){
                client.close();
            }
        }
    }
    
    class LinkWriter extends PrintWriter {
        ByteArrayOutputStream buffer;
        Writer client;
        
        LinkWriter (Writer client , ByteArrayOutputStream buffer) {
            super(buffer);
            this.buffer = buffer;
            this.client = client;
        }
        
        public void close() {
            try {
                flush();
                client.write(linkText(buffer.toString()));
                client.close();
            } catch (IOException e) {
                setError();
            }
        }
        
        String linkText(String text) {
            Enumeration en = filterconfig.getInitParameterNames();
            while(en.hasMoreElements()){
                String pattern = (String)en.nextElement();
                String value = filterconfig.getInitParameter(pattern);
                text = text.replace(pattern, pattern+"官网:<a href=" + value + ">"+value+"</a>");
            }
            return text;
        }
    }
}
