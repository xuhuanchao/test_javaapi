package com.xhc.test.javaapi.net.socket.request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        try {
            Socket server = new Socket(args[0], Integer.parseInt(args[1]));
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            
            out.writeObject(new DateRequest());
            out.flush();
            System.out.println(in.readObject());

            out.writeObject(new MyCalculation(2));
            out.flush();
            System.out.println(in.readObject());
            
            server.close();
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" I/O error " + e);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
