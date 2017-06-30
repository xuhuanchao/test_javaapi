package com.xhc.test.javaapi.rmi2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyClientAsync extends UnicastRemoteObject implements WorkListener {

    
    public MyClientAsync(String host) throws RemoteException {
        super();
        try {
            ServerRemote server = (ServerRemote)Naming.lookup("rmi://" + host + "/NiftyServer");
            server.asyncExecute(new MyCalculation(100), this);
            System.out.println("call done...");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void workCompleted(WorkRequest request, Object result) throws RemoteException {
        System.out.println("Async result: " + result);
    }

    
    public static void main(String[] args) throws RemoteException {
//        System.setSecurityManager(new RMISecurityManager());
        new MyClientAsync(args[0]);
    }
}
