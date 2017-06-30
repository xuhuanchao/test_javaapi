package com.xhc.test.javaapi.net.socket.request;

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
