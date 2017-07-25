package com.xhc.test.javaapi.io.serializable.framework;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.xhc.test.javaapi.io.serializable.Person;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NetSfJsonTester implements ITester{
    
    @Override
    public Object doSerializableTest(Object object, int n)  throws Exception{
        JSONArray jsonArray = new JSONArray();
        
        long begin = System.currentTimeMillis();
        
        for(int i=0; i<n ; i++){
            JSONObject jsonObj = JSONObject.fromObject(object);
            jsonArray.add(jsonObj);
        }
        
        long time = System.currentTimeMillis() - begin;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos, true);
        jsonArray.write(pw);
        pw.flush();
        
        System.out.printf("使用net.sf.json 进行序列化操作，序列化对象[%s] %d次 ，共用时: %d ms ，序列化对象大小: %d \n" , object , n , time, baos.size());
        return jsonArray;
    }

    @Override
    public Object doUnSerializableTest(Object serializeObj) throws Exception {
        if(serializeObj instanceof JSONArray){
            List<Person> list = new ArrayList<Person>();
            JSONArray jsonArray = (JSONArray)serializeObj;
            
            long begin = System.currentTimeMillis();
            for(Object obj : jsonArray){
                Person p = (Person)JSONObject.toBean((JSONObject)obj, Person.class);
                list.add(p);
            }
            long time = System.currentTimeMillis() - begin;
            System.out.printf("使用net.sf.json 进行反序列化操作 ，共用时: %d ms \n" , time);
            
            return list;
        }else{
            return null;
        }
        
    }

    
}
