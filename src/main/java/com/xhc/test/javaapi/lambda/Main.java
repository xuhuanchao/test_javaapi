package com.xhc.test.javaapi.lambda;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * 
 * lambda表达式本质上是一个匿名方法
 * 可以用lambda来实现一个 函数接口，lambda表达式必须赋值给一个 函数接口
 * 函数接口 一般使用@FunctionalInterface标注，或者不用这个标注，但是这个接口内只有一个抽象方法
 * 如果这个方法有返回值可以这样写：
 * (int x,int y)->x+y;    或者 (x,y)->{return x+y;};  只有一个参数可以省略()，例如 c->return c.size();
 * 如果没有参数也没有返回值可以这样写：
 * ()->{System.out.println("a");}
 * 
 * 例如：
 * Runnable runnable = ()->{System.out.println("hello");};
 * 
 * @author xhc
 *
 */
public class Main {
    
    @FunctionalInterface
    interface MyFunctionalInterface{

        public String changeValue(String s1, String s2);
    }
    
    /**
     * 简单的在函数接口中使用lambda
     */
    private static void test1(){
        Runnable r1 = () -> System.out.println("hello world");
        new Thread(r1).start();
        
        MyFunctionalInterface m1 = (s1 ,s2)->s1+s2;
        System.out.println(m1.changeValue("1", "2"));
        
        Function<String, Date> f1 = (s)->{
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
            } catch (ParseException e) {
                return new Date();
            }
        };
        System.out.println(f1.apply("2017-08-14 19:30:53"));
        System.out.println("test");
    }
    
    
    /**
     * 在集合中的Stream 中使用lambda
     */
    private static void test2(){
    
    
    }
    
    
    public static void main(String[] args) {
        
    }
}

