package com.xhc.test.javaapi.rmi2;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class MyStringIterator extends UnicastRemoteObject implements StringIterator {
    
    String[] list;
    int index = 0;
    
    public MyStringIterator(String[] list)
            throws RemoteException {
        this.list = list;
    }

    @Override
    public boolean hasNext() throws RemoteException {
        return index < list.length;
    }

    @Override
    public String next() throws RemoteException {
        return list[index++];
    }
    
    
}
