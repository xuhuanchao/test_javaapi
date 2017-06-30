package com.xhc.test.javaapi.nio;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * nio 简单例子：
 * 使用Path 对象构建FileChannel, 使用ByteBuffer 接收从FileChannel 读取的字节， 
 * 使用CharsetDecoder 将byteBuffer 解码为charBuffer
 * @author Administrator
 *
 */

public class Test {

    public static void main(String[] args) throws Exception {
        Charset charset = Charset.forName("gbk");
        IFileRwAble fileRw = new FileRwPart();
        String basepath = Class.class.getClass().getResource("/").getPath();
        String filepath = basepath + "javaapi/nio/测试文件.txt";
        ByteBuffer buff = fileRw.readFile(filepath, charset);

        System.out.println(new String(buff.array(), "gbk"));
        
        
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        ByteBuffer b = ByteBuffer.wrap(inputStr.getBytes(charset));
        fileRw.writeFile(filepath, b);
        
        buff = fileRw.readFile(filepath, charset);
        System.out.println(new String(buff.array(), charset));
    }
}
