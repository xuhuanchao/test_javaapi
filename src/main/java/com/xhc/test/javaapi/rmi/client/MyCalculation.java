package com.xhc.test.javaapi.rmi.client;

import com.xhc.test.javaapi.rmi.WorkRequest;

public class MyCalculation extends WorkRequest {

    int n;
    
    public MyCalculation(int n) {
        super();
        this.n = n;
    }


    @Override
    public Object execute() {
        return new Integer(n*n);
    }

}
