package com.xhc.test.javaapi.rmi.rmi3.demo;

public class Client_Person {

    public static void main(String[] args) {
        IPerson person = new PersonStub();
        
        System.out.println(person.sayHello("zhangsan"));
    }
}
