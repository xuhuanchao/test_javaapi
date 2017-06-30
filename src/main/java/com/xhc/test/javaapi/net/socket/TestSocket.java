package com.xhc.test.javaapi.net.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


/**
 * 简单的socket server client通讯测试
 * 
 * 练习功能：
 * 使用InpustStream 、 OutputStream 读字节、写一个字节 
 * 使用PrintWriter 输出字符串
 * 使用InputStream 为参数构造 InputStreamReader对象 ，并用Reader对象构造 BufferReader
 * 使用BufferReader 读取一行字符串
 * 使用ObjectInputStream 、 ObjectOutPutStream 读取和发送一个Object对象
 * 
 *  
 * @author Administrator
 *
 */
public class TestSocket {

    
    public void client() {
        try {
            Socket server = new Socket("localhost", 1234);
            InputStream in = server.getInputStream();
            OutputStream out = server.getOutputStream();
            
            System.out.println("client write int:"+42);
            out.write(42);
            
            PrintWriter pout = new PrintWriter(out, true);
            System.out.println("client write string:HELLO!");
            pout.println("HELLO!");
            
            byte back = (byte)in.read();
            System.out.println("client read byte:"+back);
            
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String response = bin.readLine();
            System.out.println("client read string:"+response);
            
            ObjectOutputStream oout = new ObjectOutputStream(out);
            Date date = new Date();
            System.out.println("client wirte Object:"+date);
            oout.writeObject(date);
            oout.flush();
            
            server.close();
            
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void server () {
        boolean finished = false;
        try {
            ServerSocket listener = new ServerSocket(1234);
            
            while(!finished){
                Socket client = listener.accept();
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                
                byte someByte = (byte)in.read();
                System.out.println("server read byte:" + someByte);
                
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                String someString = bin.readLine();
                System.out.println("server read line:" + someString);

                System.out.println("server write int:43");
                out.write(43);
                
                PrintWriter pout = new PrintWriter(out, true);
                System.out.println("server write string: Goodbye!");
                pout.println("Goodbye!");
                
                ObjectInputStream oin = new ObjectInputStream(in);
                Date date = (Date)oin.readObject();
                System.out.println("server read Object:" + date);
                
                client.close();
            }
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        
    }
    
    
    
    public static void main(String[] args) {
        
        TestSocket ts = new TestSocket();
        
        Thread server = new Thread(new Runnable() {
            
            @Override
            public void run() {
                ts.server();
            }
        });
        server.start();
        
        
        Thread client = new Thread(new Runnable() {
            
            @Override
            public void run() {
                ts.client();
            }
        });
        client.start();
        
        
    }

}
