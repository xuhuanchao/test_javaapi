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
import javax.servlet.http.HttpServletResponse;

@WebFilter(
    urlPatterns = "/*",
    initParams = {@WebInitParam(name="limit", value="3")}
)
public class ConlimitFilter implements Filter {

    int limit;
    volatile int count;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String s = filterConfig.getInitParameter("limit");
        if(s == null) {
            throw new ServletException("Missing init parameter: " + limit);
        }
        limit = Integer.parseInt(s);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if(count > limit){
            HttpServletResponse httpRes = (HttpServletResponse)response;
            System.out.println("服务器连接数超过最大值" + limit + "，返回503");
            httpRes.sendError(httpRes.SC_SERVICE_UNAVAILABLE, "Too Busy.");
        }else {
            ++count;
            chain.doFilter(request, response);
            --count;
        }
    }

    @Override
    public void destroy() {

    }

}
