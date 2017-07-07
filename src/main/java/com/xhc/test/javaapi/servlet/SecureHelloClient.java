package com.xhc.test.javaapi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 servlet 安全api功能，访问安全控制域内的资源会根据web.xml中配置的方式进行验证
 * 
 * @author Administrator
 *
 */
@WebServlet(urlPatterns={"/secret/cecureHello"})
@ServletSecurity(
    value=@HttpConstraint(rolesAllowed = "secretagent"),
    httpMethodConstraints = @HttpMethodConstraint(value="GET", 
    transportGuarantee = TransportGuarantee.NONE)
)
public class SecureHelloClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body><h1>You have role to access this page</h1></body></html>");
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    
}
