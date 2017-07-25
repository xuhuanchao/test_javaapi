package com.xhc.test.javaapi.io.serializable.framework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.xhc.test.javaapi.io.serializable.Person;

public class HessianTester implements ITester{

    @Override
    public Object doSerializableTest(Object object, int n) throws Exception {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        HessianOutput ho=new HessianOutput(baos);
        List<byte[]> list = new ArrayList<>(); 
        
        Long begin = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            ho.writeObject(object);
            list.add(baos.toByteArray());
        }
        long time = System.currentTimeMillis() - begin;
        System.out.printf("使用com.caucho.hessian 进行序列化操作，序列化对象[%s] %d次 ，共用时: %d ms ，序列化对象大小: %d \n" , object , n , time, baos.size());

        return list;
    }

    @Override
    public Object doUnSerializableTest(Object serializeObj) throws Exception {

        if(serializeObj instanceof List){
            List<byte[]> list = (List<byte[]>)serializeObj;
            List<Person> retList = new ArrayList<Person>();
            
            long begin = System.currentTimeMillis();
            for(byte[] bytes : list){
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                HessianInput hi = new HessianInput(bais);
                Person p = (Person)hi.readObject(Person.class);
                
                retList.add(p);
            }
            long time = System.currentTimeMillis() - begin;
            System.out.printf("使用com.caucho.hessian 进行反序列化操作 ，共用时: %d ms \n" , time);
            
            return retList;
        }else{
            return null;
        }
    }

    
}
