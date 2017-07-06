package com.xhc.test.javaapi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns={"/shoppingCart"},
asyncSupported=true)
public class ShoppingCart extends HttpServlet {

    String[] items = new String[] {
            "Chocolate Covered Crickets", "Raspberry Roaches",
            "Buttery butterflies", "Chicken Flavored Chicklets(tm)"
    };
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        HttpSession session = req.getSession();
        int[] purchases = (int[])session.getAttribute("purchases");
        if(purchases == null){
            purchases = new int[items.length]; 
            session.setAttribute("purchases", purchases);
        }
        
        out.println("<html><head><title>Shopping Cart</title><head><body><p>");
        
        if(req.getParameter("checkout") != null){
            out.println("<h1>Thanks for ordering!</h1>");
        }else {
            if(req.getParameter("add") != null){
                addPurchases(req , purchases);
                out.println("<h1>Purchase added. Please continue.</h1>");
            }else {
                if(req.getParameter("clear") != null){
                    for(int i=0; i<purchases.length; i++){
                        purchases[i] = 0;
                    }
                    out.println("<h1>Please Select Your Items!</h1>");
                }

            }
          doForm(out, req.getRequestURI());
        }
        showPurchases(out, purchases);
        out.close();
    }
    
    
    void addPurchases(HttpServletRequest request, int[] purchases) {
        for(int i=0; i<items.length; i++) {
            String added = request.getParameter(items[i]);
            if(added != null && !added.equals("")) {
                purchases[i] += Integer.parseInt(added);
            }
        }
        
    }
    
    void doForm(PrintWriter out, String requestURI) {
        out.println("<form method=POST action=" + requestURI + ">");
        for(int i=0; i<items.length; i++) {
            out.println("Quantity <input name=\"" + items[i] + "\" value=0 size=3> of: " + items[i] + "<br>");
        }
        out.println("<p><input type=submit name=add value=\"Add To Cart\">" +
        "<input type=submit name=checkout value=\"Check Out\">" + 
        "<input type=submit name=clear value=\"Clear Cart\">" + 
        "</form>");
    }
    
    void showPurchases(PrintWriter out, int[] purchases) throws IOException {
        out.println("<hr><h2>Your Shopping Basket</h2>");
        for(int i=0; i<items.length; i++) {
            if(purchases[i] != 0){
                out.println(purchases[i] + " " + items[i] + "<br>");
            }
        }
    }
    
}
