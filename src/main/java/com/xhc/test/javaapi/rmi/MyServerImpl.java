package com.xhc.test.javaapi.rmi;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;


/**
 * 远程对象调用RMI 简单例子：
 * 此例子没有实现动态类加载
 * 远程接口需要实现java.rmi.Remote接口，继承java.rmi.server.UnicastRemoteObject
 * 服务端需要将创建好的server对象绑定到注册表 Namming，需要预先启动 rmiregistry（cmd中运行） 提供服务.
 * 需要注意 rmiregistry运行的classpath需要包含注册的远程对象 
 * 
 * 如果服务端需要从远端获取对象，需要开启安全管理功能  
 * 使用参数：-Djava.security.manager 或者在代码中添加System.setSecurityManager(new RMISecurityManager());
 * java命令增加rmi和security参数
 * -Djava.rmi.server.codebase=http://localhost:8080/test_sh/testjavaapi/  指定远程对象获取来源
 * -Djava.security.policy=/f:temp/mysecurity.policy  指定安全策略文件
 * 
 * 客户端 通过Naming.lookup 方法传入参数 rmi://+主机名+/服务对象名（rmi://localhost/NiftyServer） 来获得 远端接口 Remote的一个实例
 * 运行是java命令增加 rmi和security参数，同server
 * 
 * 测试时，将com.xhc.test.javaapi.rmi包复制到其他目录，并将本工程中该目录中生成的MyCalculation.class删除掉，
 * 确保这个class在server的classpath中不存在，只能从远端加载。可以将MyCalculation.class放在一个web工程中（例如test_sh）来提供server通过http获取远程对象。
 * 运行新拷贝到目录中的client 代码
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
        try {
            Thread.sleep(2000);
            System.out.println("同步处理，等待2秒后执行方法，客户端调用execute方法也会阻塞");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return work.execute();
    }

    
    public static void main(String[] args) throws Exception{
        try {
            System.setSecurityManager(new RMISecurityManager());
            ServerRemote server = new MyServerImpl();
            Naming.rebind("NiftyServer", server);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
