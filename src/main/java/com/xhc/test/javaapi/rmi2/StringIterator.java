package com.xhc.test.javaapi.rmi2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringIterator extends Remote {

    public boolean hasNext() throws RemoteException;
    
    public String next() throws RemoteException;
}
