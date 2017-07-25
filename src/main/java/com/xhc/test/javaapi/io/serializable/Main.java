package com.xhc.test.javaapi.io.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 序列化 (Serialization)将对象的状态信息转换为可以存储或传输的形式的过程
 * java序列化 可以用来保存对象到文件，或者复制对象。
 * 序列化子类时子列的父类也要实现Serizlizable接口
 * 同一对象序列化到一个文件中多次，每次只增加5字节，java会使用应用来减少保存内容
 * 
 * @author xhc
 *
 */
public class Main {

    
    private static void serializeMutilTime() throws Exception {
        System.out.println("####多次保存同一个序列化对象到文件，java会进行优化引用同一个对象，文件大小不是翻倍成长");
        Person person = new Person("Tom", 18, 180f);
        System.out.println("原始对象：" + person);
        
        File f = new File("tom"); 
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(person);
        oos.flush();
        oos.writeObject(person);
        oos.flush();
        
        Person p1 = new Person("mike", 25, 176.6f);
        oos.writeObject(p1);
        oos.flush();

        System.out.println("序列化文件大小：" + f.length());
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Person desP = (Person)ois.readObject();
        System.out.println("反序列化后的对象：" + desP);
        
        desP = (Person)ois.readObject();
        System.out.println("反序列化后的对象：" + desP);
    }
    
    private static void doSerializable() throws Exception {
        System.out.println("####序列化java对象到文件，在反序列化为对象");
        Student s1 = new Student("小明", 20 , 178.9f, "北京大学", "三班");
        System.out.println("原始数据 = \n" + s1);
        
        String path = Main.class.getResource("/").getPath();
        File f = new File(path + "s1.ser"); 
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        
        oos.writeObject(s1);
        oos.flush();
        oos.close();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Student ss = (Student)ois.readObject();
        ois.close();
        System.out.println("反序列化后的数据 = \n" +ss);
        System.out.println("s1 == ss ? " + (s1 == ss));
    }
    
    
    private static void copy() throws Exception {
        System.out.println("\n####序列化java对象保存在Byte数组，在从Byte数组反序列化为java对象，相当于复制java对象");
        Student s1 = new Student("小明", 20 , 178.9f, "北京大学", "三班");
        System.out.println("原始数据 = \n" + s1);
        
        ByteArrayOutputStream bytesOs = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytesOs);
        
        oos.writeObject(s1);
        oos.flush();
        oos.close();
        
        ByteArrayInputStream bytesIs = new ByteArrayInputStream(bytesOs.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bytesIs);
        Student ss = (Student)ois.readObject();
        ois.close();
        System.out.println("反序列化后的数据 = \n" +ss);
        System.out.println("s1 == ss ? " + (s1 == ss));
    }
    
    public static void main(String[] args) throws Exception {
        serializeMutilTime();
//        doSerializable();
//        copy();
    }
    
}
