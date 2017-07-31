package com.xhc.test.javaapi.rmi.rmi3.demo;

public class Server_Person {

    public static void main(String[] args) {
        IPerson person = new PersonImpl();
        PersonSkeleton personSkeleton = new PersonSkeleton(person);
        personSkeleton.start();
        System.out.println("person skeleton started");
    }
}
