package com.xhc.test.javaapi.rmi.rmi3.demo;

public class PersonImpl implements IPerson {

    @Override
    public String sayHello(String name) {
        return "hello "+name;
    }

}
