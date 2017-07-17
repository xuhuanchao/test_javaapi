package com.xhc.test.javax.xml.ws;

import com.xhc.test.javax.xml.wsimpl.Echo;
import com.xhc.test.javax.xml.wsimpl.EchoService;
import com.xhc.test.javax.xml.wsimpl.MyObject;

/**
 * 测试 使用注解发布的WebService， 使用的ws接口是使用 wsimport 命令生成的
 * @author Administrator
 *
 */
public class EchoClient {

    public static void main(String[] args) {
        Echo service= new EchoService().getEchoPort();
        int i = service.echoInt(42);
        System.out.println(i);
        String s = service.echoString("Hello!");
        System.out.println(s);
        MyObject myObject = new MyObject();
        myObject.setIntValue(42);
        myObject.setStringValue("Foo!");
        MyObject myObj = service.echoMyObject(myObject);
        System.out.println(myObj.getIntValue() + " - " + myObj.getStringValue());
    }

}
