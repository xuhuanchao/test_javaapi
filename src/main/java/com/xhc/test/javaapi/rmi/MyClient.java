package com.xhc.test.javaapi.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

/**
 * 远程对象调用：
 * 通过Naming.lookup("URL"); 获得远程对象接口 Remote，之后转为其他自定义类来进行操 作
 *  
 * @author Administrator
 *
 */

public class MyClient {

    public static void main(String[] args) throws RemoteException{
        new MyClient(args[0]);  // localhost
    }
    
    public MyClient(String host) {
        try {
            ServerRemote server = (ServerRemote)Naming.lookup("rmi://" + host + "/NiftyServer");
            System.out.println(server.getDate());
            System.out.println(server.execute(new MyCalculation(2)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
