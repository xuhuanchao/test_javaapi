package com.xhc.test.javaapi.io.serializable.framework;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xhc.test.javaapi.io.serializable.Person;

import net.sf.json.JSONObject;

public class AlibabaFastjsonTester implements ITester {

    @Override
    public Object doSerializableTest(Object object, int n) throws Exception {

        List<String> list = new ArrayList<String>();
        long begin = System.currentTimeMillis();
        
        for(int i=0; i<n ; i++){
            String json = JSON.toJSONString(object);
            list.add(json);
        }
        
        long time = System.currentTimeMillis() - begin;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        oos.flush();
        
        System.out.printf("使用com.alibaba.fastjson 进行序列化操作，序列化对象[%s] %d次 ，共用时: %d ms ，序列化对象大小: %d \n" , object , n , time, baos.size());
        return list;
    }

    @Override
    public Object doUnSerializableTest(Object serializeObj) throws Exception {

        if(serializeObj instanceof List){
            List<String> list = (List<String>)serializeObj;
            List<Person> retList = new ArrayList<Person>();
            
            long begin = System.currentTimeMillis();
            for(String s : list){
                Person p = JSON.parseObject(s, Person.class);
                retList.add(p);
            }
            long time = System.currentTimeMillis() - begin;
            System.out.printf("使用com.alibaba.fastjson 进行反序列化操作 ，共用时: %d ms \n" , time);

            
            return retList;
        }else {
            return null;
        }
    }

}
