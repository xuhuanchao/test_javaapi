package com.xhc.test.javaapi.net.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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



/**
 * 简单的http 服务处理功能
 * 使用BufferInputStream 缓存请求的byte数据并打印请求内容，
 * 使用BufferInputStream 的mark定位 ，使用rest 恢复当前位置到mark，以便重新读取byte， 
 * mark(int) 的int参数要足够大如果bis 读取的byte长度超出了int， 则mark标记的位置将失效rest不能恢复到mark位置，
 * 例外是 如果BufferIputStream 的缓冲区长度比int大，读取int个byte后没有超出缓冲区，则rest仍然可以恢复到mark位置。
 * 如果是GET请求将资源返回给客户端
 * 
 * @author Administrator
 *
 */

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
				InputStream inputStream = client.getInputStream();
			    BufferedInputStream bis = new BufferedInputStream(inputStream);
			    bis.mark(1000);
			    byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, bytes.length);
                System.out.println("请求内容：" + new String(bytes, "utf-8"));
                bis.reset();
                
                
			    BufferedReader in = new BufferedReader(new InputStreamReader(bis, "8859_1"));
				OutputStream out = client.getOutputStream();
				PrintWriter pout = new PrintWriter(new OutputStreamWriter(out, "8859_1"), true);
				String request = in.readLine();
				System.out.println("Request: " + request);
				
				
				
				Matcher get = Pattern.compile("GET /?(\\S*).*").matcher(request);
				if(get.matches()){
					request = get.group(1);
					if(request.endsWith("/") || request.equals("")){
						request =request + "index.html";
					}
					
					try {
						FileInputStream fis = new FileInputStream(request);
						byte[] data = new byte[64*1024];
						for(int read; (read = fis.read(data)) > -1 ; ){
							out.write(data, 0, read);
						}
						out.flush();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						System.out.println(" File not found.");
						pout.println("404 Object Not Found");
					}
				}else{
					pout.print("400 Bad Request");
				}
				client.close();
				System.out.println("请求处理完毕。");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				System.out.println(" encoding error " + e);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(" I/O error " + e);
			} 
			
		}
	}
}

