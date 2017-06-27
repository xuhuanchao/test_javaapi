package com.xhc.test.javaapi.net.udp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TinyHttpd {

	public static void main(String[] args) throws IOException {
		Executor executor = Executors.newFixedThreadPool(3);
		ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
		while(true){
			executor.execute(new TinyHttpd().new TinyHttpdConnection(ss.accept()));
		}
	}
	
	class TinyHttpdConnection implements Runnable {
		Socket client;
		public TinyHttpdConnection(Socket client) {
			this.client = client;
		}
		
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "8859_1"));
				OutputStream out = client.getOutputStream();
				PrintWriter pout = new PrintWriter(new OutputStreamWriter(out, "8859_1"), true);
				String request = in.readLine();
				System.out.println("Request: " + request);
				
				Matcher get = Pattern.compile("GET /?(\\S*).*").matcher(request);
				if(get.matches()){
					request = get.group(1);
					if(request.endsWith("/") || request.equals("")){
						request = request + "index.html";
					}
					String classpath = getClass().getResource(".").getFile().toString();
					request = classpath + request;
					System.out.println("real request path:" + request);

					try {
						FileInputStream fis = new FileInputStream(request);
						byte[] data = new byte[64*1024];
						for(int read; (read = fis.read(data)) > -1 ; ){
							out.write(data, 0, read);
						}
						out.flush();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						pout.println("404 Object Not Found");
					}
				}else{
					pout.print("400 Bad Request");
				}
				client.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}

