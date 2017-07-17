package com.xhc.test.javaapi.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@WebFilter(
    servletNames="waitservlet1",
    initParams = {
        @WebInitParam(name="time", value="3")
    }
)
public class RequestDefaultsFilter implements Filter {

    FilterConfig filterConfig;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        WrappedRequest wrappedRequest = new WrappedRequest((HttpServletRequest)request);
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {

    }

    class WrappedRequest extends HttpServletRequestWrapper {
        public WrappedRequest(HttpServletRequest req) {
            super(req);
        }
        
        public String getParameter(String name) {
            String value = super.getParameter(name);
            if(value == null){
                value = filterConfig.getInitParameter(name);
            }
            return value;
        }
    }
}
