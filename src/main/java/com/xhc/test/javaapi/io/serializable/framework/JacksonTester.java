package com.xhc.test.javaapi.io.serializable.framework;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.xhc.test.javaapi.io.serializable.Person;

public class JacksonTester implements ITester{

    @Override
    public Object doSerializableTest(Object object, int n) throws Exception { 
        List<String> list = new ArrayList<String>();

        ObjectMapper objectMapper=new ObjectMapper();

        long begin = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            String jsonStr = objectMapper.writeValueAsString(object);
            list.add(jsonStr);
        }
        long time = System.currentTimeMillis() - begin;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        oos.flush();
        
        
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
       
        for(int i=0; i<n; i++){
            byte[] b = objectMapper.writeValueAsBytes(object);
            baos2.write(b);
            baos2.flush();
        }
        
        System.out.printf("使用org.codehaus.jackson 进行序列化操作，序列化对象[%s] %d次 ，共用时: %d ms ，序列化对象大小: %d , 使用objectMapper.writeValueAsBytes 获得的byte大小：%d \n" , object , n , time, baos.size() , baos2.size());
        return list;
    }

    @Override
    public Object doUnSerializableTest(Object serializeObj) throws Exception {
        if(serializeObj instanceof List){
            ObjectMapper objectMapper=new ObjectMapper();
            List<Person> retList = new ArrayList<Person>();    
            List<String> list = (List<String>)serializeObj;
            
            long begin = System.currentTimeMillis();
            for(String s : list){
                Person p = objectMapper.readValue(s, Person.class);
                retList.add(p);
            }
            long time = System.currentTimeMillis() - begin;
            System.out.printf("使用org.codehaus.jackson 进行反序列化操作 ，共用时: %d ms \n" , time);
            
            return retList;
        }else {
            return null;
        }
        
    }
    
    
}
