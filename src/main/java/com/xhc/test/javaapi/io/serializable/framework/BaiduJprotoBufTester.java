package com.xhc.test.javaapi.io.serializable.framework;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.xhc.test.javaapi.io.serializable.Person;

public class BaiduJprotoBufTester implements ITester {

    @Override
    public Object doSerializableTest(Object object, int n) throws Exception {
        if(object instanceof Person){
            Person p = (Person)object;
            Codec<Person> personCodec= ProtobufProxy.create(Person.class,false);
            
            List<byte[]> list = new ArrayList<>(); 
            long begin = System.currentTimeMillis();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for(int i=0; i<n; i++){
                 byte[] bytes=personCodec.encode(p);
                 baos.write(bytes);
                 baos.flush();
                 list.add(bytes);
            }
            long time = System.currentTimeMillis() - begin;
            System.out.printf("使用com.baidu.jprotobuf 进行序列化操作，序列化对象[%s] %d次 ，共用时: %d ms ，序列化对象大小: %d \n" , object , n , time, baos.size());
            return list;
        }else{
            return null;
        }
        
    }

    @Override
    public Object doUnSerializableTest(Object serializeObj) throws Exception {
        if(serializeObj instanceof List){
            Codec<Person> personCodec= ProtobufProxy.create(Person.class,false);
            List<Person> retList = new ArrayList<Person>();
            
            List<byte[]> list = (List<byte[]>)serializeObj;
            
            long begin = System.currentTimeMillis();
            for(byte[] bytes : list){
                Person p = personCodec.decode(bytes);
                retList.add(p);
            }
            long time = System.currentTimeMillis() - begin;
            System.out.printf("使用com.baidu.jprotobuf 进行反序列化操作 ，共用时: %d ms \n" , time);
            
            return retList;
        }else {
            return null;
        }
    }

    
}
