package com.xhc.test.javaapi.net.socket.request;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * socket 服务 接收客户端请求，判断Request 对象 类型，进行不同操作。
 * 可以用来执行客户端请求的Request包含的方法
 * @author Administrator
 *
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
        while(true){
            new Server().new ServerConnection(ss.accept()).start();
        }
    }
    
    
    class ServerConnection extends Thread {
        Socket client;

        public ServerConnection(Socket client) {
            super();
            this.client = client;
        }
        
        @Override
        public void run () {
            try {
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                while(true){
                    out.writeObject(processRequest(in.readObject()));
                    out.flush();
                }
            } catch (EOFException e){
                try {
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(" I/O error " + e);
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
        }
        
        
        private Object processRequest(Object request) {
            if(request instanceof DateRequest){
                return new Date();
            }else if(request instanceof WorkRequest) {
                return ((WorkRequest)request).execute();
            }else {
                return null;
            }
        }
        
    }
}
