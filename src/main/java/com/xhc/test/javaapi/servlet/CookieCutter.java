package com.xhc.test.javaapi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试cookie的使用
 * @author Administrator
 *
 */
public class CookieCutter extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        if(req.getParameter("setcookie") != null){
            Cookie cookie = new Cookie("Learningjava", "Cookies!");
            cookie.setMaxAge(3600);
            resp.addCookie(cookie);
            out.println("<html><body><h1>Cookie Set...</h1>");
        } else {
            out.println("<html><body>");
            Cookie[] cookies = req.getCookies();
            if(cookies == null || cookies.length == 0) {
                out.println("<h1>No cookies found...</h1>");
            }else {
                for(int i=0; i<cookies.length; i++){
                    out.println("<h1>Name: " + cookies[i].getName() + "<br>" +
                            "Value: " + cookies[i].getValue() + "</h1>");
                }
            }
            out.println("<p><a href=\"" + req.getRequestURI() + "?setcookie=true\">" + "Reset the Learning Java cookie.</a></p>" );
        }
        out.println("</body></html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    
}
