package com.xhc.test.javaapi.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="testResponseServlet",
        urlPatterns="/testResponseFilter"
)
public class TestResponseFilter implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=utf-8");
        PrintWriter out = res.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body><h1>Test Response Filter</h1>");
        sb.append("<table><tr><td>Oracle</td><td>Mysql</td><td>Redis</td><td>Mongodb</td></tr></table>");
        sb.append("</body></html>");
        out.println(sb.toString());
        out.close();
    }

    @Override
    public String getServletInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
