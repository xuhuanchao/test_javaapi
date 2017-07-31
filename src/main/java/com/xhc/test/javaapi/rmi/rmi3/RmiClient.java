package com.xhc.test.javaapi.rmi.rmi3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 从Naming中根据rmi的uri找到服务对象并使用
 * @author Administrator
 *
 */
public class RmiClient {

    public static void main(String[] args) {
        try {
            IWork work = (IWork)Naming.lookup("rmi://localhost:8765/dowork");
            System.out.println("获取到远程对象："+work);
            
            System.out.println(work.dowork("learning"));
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
