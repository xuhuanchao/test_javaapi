package com.xhc.test.javaapi.io.serializable.framework;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import com.xhc.test.javaapi.io.serializable.Person;


/**
 * 测试 net.sf.json \ jackson \ fastjson \ jprotobuf \ hessian 的java对象转json或者byte的使用，以及反向转换的使用
 * 
 * @author xhc
 *
 */
public class Main {

    
    public static void main(String[] args) throws Exception {
        
        Person p1 = new Person("张三丰", 50, 172.5f);
        int n = 1000;
        
        ITester jacksonTester = new JacksonTester();
        Object ret1 = jacksonTester.doSerializableTest(p1, n);
//        System.out.println(ret1);
        jacksonTester.doUnSerializableTest(ret1);
        
        System.out.println("\n==================\n");
        
        ITester netSfJsonTester = new NetSfJsonTester();
        Object ret2 = netSfJsonTester.doSerializableTest(p1, n);
//        System.out.println(ret2);

        Object o = netSfJsonTester.doUnSerializableTest(ret2);
        
        System.out.println("\n==================\n");
        
        ITester fastjson = new AlibabaFastjsonTester();
        Object ret3 = fastjson.doSerializableTest(p1, n);
//        System.out.println(ret3);
        fastjson.doUnSerializableTest(ret3);
        
        System.out.println("\n==================\n");
        
        ITester baiduJprotoBuf = new BaiduJprotoBufTester();
        Object ret4 = baiduJprotoBuf.doSerializableTest(p1, n);
        baiduJprotoBuf.doUnSerializableTest(ret4);
        
        System.out.println("\n==================\n");
        
        ITester hessianTester = new HessianTester();
        Object ret5 = hessianTester.doSerializableTest(p1, n);
        hessianTester.doUnSerializableTest(ret5);
    }
}
