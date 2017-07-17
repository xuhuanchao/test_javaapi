package com.xhc.test.javax.xml.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * 使用注解发布WebService
 * @author Administrator
 *
 */
@WebService
public class Echo {

    @WebMethod
    public int echoInt(int value){
        return value;
    }
    
    @WebMethod
    public String echoString(String value){
        return value;
    }
    
    @WebMethod
    public MyObject echoMyObject(MyObject value){
        return value;
    }
    
    public static void main(String[] args) {
        Endpoint endpoint = Endpoint.publish("http://localhost:8081/echo",  new Echo());
    }
    
    
    
    
}
