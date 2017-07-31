package com.xhc.test.javaapi.rmi.rmi3;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class WorkImpl extends UnicastRemoteObject implements IWork{

    
    public WorkImpl() throws RemoteException {
        super();
    }


    @Override
    public String dowork(String name) throws RemoteException {
        return "start do work "+name+"...";
    }

    
}
