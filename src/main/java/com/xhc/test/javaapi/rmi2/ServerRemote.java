package com.xhc.test.javaapi.rmi2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemote extends Remote {

    StringIterator getList() throws RemoteException;
    
    void asyncExecute(WorkRequest work, WorkListener listenser) throws RemoteException;
}
