package com.xhc.test.javaapi.rmi.rmi3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWork extends Remote {

    public String dowork(String name) throws RemoteException;
}
