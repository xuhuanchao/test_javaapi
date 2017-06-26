package com.xhc.test.javaapi.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

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
