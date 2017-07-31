package com.xhc.test.javaapi.rmi.rmi3.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PersonSkeleton extends Thread{

    IPerson person ;
    
    int port = 8811;

    public PersonSkeleton(IPerson person) {
        this.person = person;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            ServerSocket server = new ServerSocket(port);
            
            Socket socket = server.accept();
            is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            String method = (String)ois.readObject();
            
            if(method.equals("sayHello")){
                String name = (String)ois.readObject();
                String result = person.sayHello(name);
                
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(result);
                oos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } 
    }
    
}
