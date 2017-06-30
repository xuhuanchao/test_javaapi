package com.xhc.test.javaapi.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP 数据报 简单例子：
 * main启动数据报监听，获取UDP发送来的数据
 * @author Administrator
 *
 */

public class Pulse {

	public static void main(String[] args) throws IOException {
		System.out.println("Pulse starting..");
		DatagramSocket s = new DatagramSocket(Integer.parseInt(args[0]));
		
		while(true) {
			System.out.println("prepare receive");
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			s.receive(packet);
			String message = new String(packet.getData(), 0, packet.getLength(), "utf-8");
			System.out.println("Heartbeat from: "+ packet.getAddress().getHostName() + " - " + message);
		}
	}
}
