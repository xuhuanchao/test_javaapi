package com.xhc.test.javaapi.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WaitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String waitStr = req.getParameter("time");
        if(waitStr == null){
            throw new ServletException("Missing parameter: time");
        }
        int wait = Integer.parseInt(waitStr);
        try {
            Thread.sleep(wait * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body><h1>WaitServlet Response</h1></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    
}
