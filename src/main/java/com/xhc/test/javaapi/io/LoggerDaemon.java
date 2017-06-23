package com.xhc.test.javaapi.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;

/**
 * java学习指南 Learning Java
 * 管道 练习
 * @author Administrator
 *
 */
public class LoggerDaemon extends Thread{

    PipedReader in = new PipedReader();
    PrintWriter pw;
    
    public LoggerDaemon() throws IOException{
        pw = new PrintWriter(new PipedWriter(in));
        start();
    }
    
    public void run() {
        BufferedReader bin = new BufferedReader(in);
        String s;
        try{
            while((s = bin.readLine()) != null){
                System.out.println("从管道接收到了数据："+s);
            }
        }catch(IOException e){
            System.out.println("管道接收数据报错");
            e.printStackTrace();
        }
    }
    
    PrintWriter getWriter() throws IOException {
        return pw;
    }
    
    
    //
    //  test
    //
    public static void main(String[] args) throws IOException{
        LoggerDaemon ld = new LoggerDaemon();
        PrintWriter out = ld.getWriter();
        out.println("Application starting...");
        out.close();
    }
}
