package com.xhc.test.javaapi.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;


/**
 * 远程对象调用RMI 简单例子：
 * 此例子没有实现动态类加载
 * 远程接口需要实现java.rmi.Remote接口，继承java.rmi.server.UnicastRemoteObject
 * 服务端需要将创建爱你的server对象绑定到注册表 Namming，并且启动 rmiregistry（cmd中运行） 提供服务.
 * 需要注意 rmiregistry运行的classpath中需要可以访问到 
 * 客户端 通过Naming.lookup 方法传入参数 rmi://+主机名+/服务对象名 来获得 远端接口 Remote的一个实例
 * rmi://localhost/NiftyServer
 * 
 * @author Administrator
 *
 */

public class MyServerImpl extends UnicastRemoteObject implements ServerRemote {

    
    
    public MyServerImpl() throws RemoteException {
        super();
    }


    @Override
    public Date getDate() throws RemoteException {
        return new Date();
    }

    @Override
    public Object execute(WorkRequest work) throws RemoteException {
        return work.execute();
    }

    
    public static void main(String[] args) {
        try {
            ServerRemote server = new MyServerImpl();
            Naming.rebind("NiftyServer", server);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
