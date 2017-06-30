package com.xhc.test.javaapi.rmi2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WorkListener extends Remote {

    public void workCompleted(WorkRequest request, Object result) throws RemoteException;
}
