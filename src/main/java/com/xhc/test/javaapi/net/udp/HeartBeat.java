package com.xhc.test.javaapi.net.udp;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JApplet;
import javax.swing.JLabel;

public class HeartBeat extends JApplet {

	String myHost;
	int myPort;
	
	public void init(){
		myHost = getCodeBase().getHost();
		myPort = Integer.parseInt(getParameter("myPort"));
		System.out.println(" Applet inited .");
		
		JLabel label = new JLabel("Hello Word1");
		label.setFont(new Font("", 1, 30));
		label.setForeground(Color.red);
		//this.getContentPane().add(label);
		this.add(label);
	}

	private void sendMessage(String message){
		try {
			byte[] data = message.getBytes("utf-8");
			InetAddress addr = InetAddress.getByName(myHost);
			DatagramPacket packet = new DatagramPacket(data, data.length, addr, myPort);
			DatagramSocket ds = new DatagramSocket();
			ds.send(packet);
			ds.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		sendMessage("Arrived");
	}
	
	public void stop() {
		sendMessage("Departed");
	}
}
