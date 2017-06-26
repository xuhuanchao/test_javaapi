package com.xhc.test.javaapi.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public interface IFileRwAble {

    
    public ByteBuffer readFile(String filepath, Charset charset) throws Exception ;
    
    
    public void writeFile(String filepath, ByteBuffer bbuff) throws Exception;
}
