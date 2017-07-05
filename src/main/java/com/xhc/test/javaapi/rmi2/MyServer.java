package com.xhc.test.javaapi.rmi2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.xhc.test.javaapi.rmi2.ServerRemote;

/**
 * 异步处理远程对象
 * client作为UnicastRemoteObject ，调用server的Remote方法时将自己和其他工作对象作为参数传给Server
 * Server另起线程处理处理业务,client可以直接返回对server的方法调用，
 * server线程处理完业务后会调用client的某方法通知client
 * @author Administrator
 *
 */

public class MyServer extends UnicastRemoteObject implements ServerRemote{

    public MyServer() throws RemoteException {
        super();
    }

    @Override
    public StringIterator getList() throws RemoteException {
        return new MyStringIterator(new String[] {"Foo" , "Bar" , "Gee"});
    }

    @Override
    public void asyncExecute(WorkRequest request, WorkListener listenser) throws RemoteException {
        new Thread() {
            public void run(){
                Object result = request.execute();
                try {
                    listenser.workCompleted(request, result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    System.out.println( e);
                }        
            }
        }.start();
        
    }

    
    public static void main(String[] args) {
        try {
            System.setSecurityManager(new RMISecurityManager());
            ServerRemote server = new MyServer();
            Naming.rebind("NiftyServer", server);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
