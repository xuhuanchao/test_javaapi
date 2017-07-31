package com.xhc.test.javaapi.rmi.rmi3;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 使用LocateRegistry 注册端口， Naming绑定一个rui和服务对象
 * RMI会为服务端和客户端生成代理类 Skeleton（骨架） 和 Stub（存根）来处理网络和系统的通讯，对使用者透明，
 * 客户端只需要从Naming中获得服务对象并使用
 * @author xhc
 *
 */
public class RmiServer {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8765);
            IWork work = new WorkImpl();
            Naming.bind("rmi://localhost:8765/dowork", work);
            
            System.out.println("RMI server started.");
        
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
}
