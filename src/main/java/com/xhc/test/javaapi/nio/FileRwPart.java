package com.xhc.test.javaapi.nio;

import java.io.FileInputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.FileSystem;

public class FileRwPart implements IFileRwAble {

    @Override
    public ByteBuffer readFile(String filepath, Charset charset) throws Exception {
        ByteBuffer bbuff = null;
        Path p = Paths.get(new URI("file://" + filepath));
        
        try(FileChannel channel = FileChannel.open(p)){
            bbuff = ByteBuffer.allocate((int)channel.size());
            while(channel.read(bbuff) > 0){
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer cb = decoder.decode(bbuff);
            }

        }
        return bbuff;
    }

    @Override
    public void writeFile(String filepath, ByteBuffer bbuff) throws Exception {
        Path p = Paths.get(new URI("file://" + filepath));
        try(FileChannel channel = FileChannel.open(p, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            channel.write(bbuff);
        }
    }

}
