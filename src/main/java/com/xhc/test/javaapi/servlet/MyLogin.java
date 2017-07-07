package com.xhc.test.javaapi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试使用servlet api 手动验证用户
 * 
 * @author Administrator
 *
 */
@WebServlet(urlPatterns = "/mylogin")
@ServletSecurity(
        httpMethodConstraints = @HttpMethodConstraint(value = "POST", 
            transportGuarantee = TransportGuarantee.CONFIDENTIAL
        )
)
public class MyLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String type = req.getParameter("type");
        if(type.equals("in")){
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            req.login(username, password);
            String reqUri = req.getRequestURI();
            String path = reqUri.substring(0, reqUri.lastIndexOf("/") );
            out.println("<html><body><h1>Thank you for using. You are logged in now.</h1>"+
                    "<a href=\""+path+"/secret/secret1.html\" >访问其他安全域内的页面</a><br/>"+
                    "<a href=\""+path+"/login?type=out\">登出</a></body></html>");
        }else {
            out.println("<html><body><h1>Thank you for using. You are logout now.</h1></body></html>");
        }
        
        
        
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    
    
}
