package com.xhc.test.javaapi.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class TestIO {

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dao = new DataOutputStream(bao);
        dao.writeInt(16777216);
        dao.flush();
        
        byte[] bytes = bao.toByteArray();
        for(byte b : bytes){
            System.out.println(b);
        }

//        Thread
        
        System.out.println(ByteOrder.nativeOrder());
        
        Map map = Charset.availableCharsets();
        Set entrySet = map.entrySet();
        Iterator iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Entry entry = (Entry)iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
