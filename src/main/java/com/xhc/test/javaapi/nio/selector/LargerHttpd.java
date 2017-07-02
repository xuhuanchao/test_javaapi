package com.xhc.test.javaapi.nio.selector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 测试非阻塞通讯，使用Selector.open()，创建一个Selector，
 * SocketChannel、ServerSocketChannle、DatagramChannel 向Selector注册，
 * 注册时提供SelectionKey 表示Selector 关心的事件， 比如: OP_ACCPET \ OP_WRITE \ OP_CONNECT \ OP_READ
 * SelectiontKey ： 可以使用intersetOpe()修改 关注点，可以使用cancle()解除向Selector的注册，
 *      可以获得当前关注点 isReadable() \ isWritable() \ isConnectable() \ isAcceptable()
 *  
 *  selector.select(long) 等待毫秒时间 来获得一个准备集，包含可用的已注册的channel，
 *  selector.selectedKeys 获得可用channel的 selectionKey集合
 *  
 *  分3步处理一个请求socketChannel ，
 *  1.accpet： 接受请求，封装socketChannel 到一个httpConnection 对象
 *  2.read：分析请求内容得到具体请求文件名 
 *  3.write：将内容结果内容输出给客户端
 *   
 * @author xhc
 *
 */
public class LargerHttpd {
    
    Selector clientSelector;
    
    
    public void run(int port , int threads) throws IOException {
        clientSelector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress sa = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        ssc.socket().bind(sa);
        ssc.register(clientSelector, SelectionKey.OP_ACCEPT);
        
        Executor executor = Executors.newFixedThreadPool(threads);
        
        while(true) {
            
            try{
                while(clientSelector.select(100) == 0);
                Set<SelectionKey> readySet = clientSelector.selectedKeys();
                for(Iterator<SelectionKey> it = readySet.iterator(); it.hasNext();){
                    final SelectionKey key = it.next();
                    it.remove();
                    if(key.isAcceptable()){
                        acceptClient(ssc);
                    } else {
                        key.interestOps(0);
                        executor.execute(new Runnable() {
                            
                            @Override
                            public void run() {
                                try {
                                    handleClient(key);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println(e);
                                }
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }
    
    
    void acceptClient(ServerSocketChannel ssc) throws IOException {
        SocketChannel clientSocket = ssc.accept();
        clientSocket.configureBlocking(false);
        SelectionKey key = clientSocket.register(clientSelector, SelectionKey.OP_READ);
        HttpdConnection client = new HttpdConnection(clientSocket);
        key.attach(client);
        
    }
    
    void handleClient(SelectionKey key) throws IOException {
        HttpdConnection client = (HttpdConnection)key.attachment();
        if(key.isReadable()){
            client.read(key);
        }else {
            client.write(key);
        }
        clientSelector.wakeup();
    }
    
    public static void main(String[] args) throws IOException {
        new LargerHttpd().run(1235, 3);
    }
}


class HttpdConnection {
    
    static Charset charset = Charset.forName("8859_1");
    static Pattern httpGetPattern = Pattern.compile("(?s)GET /?(\\S*).*");
    SocketChannel clientSocket;
    ByteBuffer buff = ByteBuffer.allocate(64*1024);
    String request;
    String response;
    FileChannel file;
    int filePosition;
    
    HttpdConnection (SocketChannel clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    void read (SelectionKey key) throws IOException {
        if(request == null && (clientSocket.read(buff) == -1 || buff.get(buff.position()-1) == '\n')) {
            processRequest(key);
        }else {
            key.interestOps(SelectionKey.OP_READ);
        }
    }
    
    
    void processRequest(SelectionKey key) {
        buff.flip();
        request = charset.decode(buff).toString();
        System.out.println("Request is :" + request);
        Matcher get = httpGetPattern.matcher(request);

        if(get.matches()) {
            if(get.groupCount() == 1){
                request = get.group(1);    
            }
            if(request.endsWith("/") || request.equals("") ) {
                request = request + "index.html";
            }
            
            System.out.println("Accpet request is:" + request);
            
            String classpath = getClass().getResource(".").getFile().toString();
            request = classpath + request;

            try {
                file = new FileInputStream(request).getChannel();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                response = "404 Object Not Found";
            }
            
        } else {
            response = "400 Bad Request";
        }
        
        if(response != null) {
            buff.clear();
            charset.newEncoder().encode(CharBuffer.wrap(response), buff, true);
            buff.flip();
        }
        key.interestOps(SelectionKey.OP_WRITE);
        
    }

    
    void write(SelectionKey key) throws IOException {
        if(response != null) {
            clientSocket.write(buff);
            if(buff.remaining() == 0){
                response = null;
            }
        } else if (file != null) {
            if(filePosition == 0){
                clientSocket.write(getHttp11Header((int)file.size()));
            }
            int remaining = (int)file.size() - filePosition;
            long sent = file.transferTo(filePosition, remaining, clientSocket);
            if(sent >= remaining || remaining <= 0) {
                file.close();
                file = null;
            }else {
                filePosition += sent;
            }
        }
        
        if(response == null && file == null) {
            clientSocket.close();
            key.cancel();
        } else {
            key.interestOps(SelectionKey.OP_WRITE);
        }
        
    }
    
    ByteBuffer getHttp11Header (int fileSize) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 200 OK\n");
        sb.append("Cache-Control: public, max-age=60\n");
        sb.append("Content-Type: text/html; charset=utf-8\n");
        sb.append("Expires: "+new Date()+"\n");
        sb.append("Last-Modified: " + new Date()+"\n");
        sb.append("X-ApsNet-version:\n");
        sb.append("X-Powered-By:\n");
        sb.append("Date: "+new Date()+"\n");
        sb.append("Content-Length: "+fileSize+"\n");
        sb.append("\n");
        try {
            return ByteBuffer.wrap(sb.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ByteBuffer.wrap(sb.toString().getBytes());
        } finally {
            System.out.println("Response HttpHeader is: " + sb.toString());
        }
    }
    
}


