package com.xhc.test.javaapi.rmi.rmi3.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PersonStub extends PersonImpl {

    
    int port = 8811;
    
    public String sayHello(String name) {
        
        try {
            Socket socket = new Socket("localhost", port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("sayHello");
            oos.flush();
            oos.writeObject(name);
            oos.flush();
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String result = (String)ois.readObject();
            
            socket.close();
            return result;
            
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
}
