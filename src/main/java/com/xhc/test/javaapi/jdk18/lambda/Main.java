
package com.xhc.test.javaapi.jdk18.lambda;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * lambda表达式本质上是一个匿名接口实现
 * 可以用lambda来实现一个 函数接口，lambda表达式的结果是一个 函数接口
 * 函数接口：一般使用@FunctionalInterface标注，或者不用这个标注，这个接口内只能有一个抽象方法
 * 如果这个方法有返回值可以这样写：
 * (int x,int y)->x+y;    或者 (x,y)->{return x+y;};  只有一个参数可以省略()，例如 c->return c.size();
 * 如果没有参数也没有返回值可以这样写：
 * ()->{System.out.println("a");}
 * 
 * 例如：
 * Runnable runnable = ()->{System.out.println("hello");};
 * 
 * java8中集合新增有stream属性@FunctionalInterface的接口在
 * Stream 接口的很多方法入参都为@FunctionalInterface型
 *
 * 例如：
 * Stream<T> filter(Predicate<? super T> predicate);
 * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
 * T reduce(T identity, BinaryOperator<T> accumulator);
 * 等
 *
 * @author xhc
 *
 */
public class Main{
    
    @FunctionalInterface
    interface MyFunctionalInterface{
        
        String changeValue(String s1, String s2);

        default String changeValue1(String s1, String s2){
            return s1 + "-" + s2;
        }
    }
    
    /**
     * 简单的在函数接口中使用lambda
     */
    private static void test1(){
        Runnable r1 = () -> System.out.println("hello world");
        new Thread(r1).start();

        //实现MyFunctionalInterface 函数接口
        MyFunctionalInterface m1 = (s1 ,s2)->s1+s2;
        System.out.println("调用抽象方法：" + m1.changeValue("a", "b"));
        System.out.println("调用默认实现方法：" + m1.changeValue1("a", "b"));


        // Function 根据指定的入参和出参实现一个方法
        Function<String, Date> f1 = (s)->{
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
            } catch (ParseException e) {
                return new Date();
            }
        };
        System.out.println(f1.apply("2017-08-14 19:30:53"));
    }
    
    
    /**
     * 在集合中的Stream 中使用lambda
     * filter过滤 、 distinct去重、 分组groupingBy， 聚合 统计collect reduce
     */
    private static void test2(){
    	String[] array = new String[]{"a", "b1", "b2", "a2", "a1", "b1", "b2", "a"};
    	List<String> list = Arrays.asList(array);

    	List<String> list2 = list.stream().filter((t)->{return t.equals("a");}).collect(Collectors.toList());
    	System.out.println("从集合"+ list + "挑选a组成新集合：" + list2);

        String s = new String();
        array = new String[]{"4", "6", "1", "2", "3", "1", "6", "5"};
    	list = Arrays.asList(array);
    	List<Integer> result = list.stream().map(t -> new Integer(t)).filter(e -> e%2 ==0).distinct().collect(Collectors.toList());
    	System.out.println("从集合"+list + ", 中获取双数并去重复后得到："+result);


    	Map map = list.stream().collect(Collectors.groupingBy(p->p, Collectors.summingInt(p ->1)));
    	System.out.println("求集合"+list + ", 中的每个元素出现个数：" +map);

        //map() 将元素修改成另一个， reduce统计元素获得一个结果
    	int sum = list.stream().map(t ->new Integer(t)).reduce(0, (x,y)->x+y);
    	System.out.println("求集合"+list + ", 中的每个元素之和：" + sum);
    }

    /**
     * lambda中使用外部变量，会将外部变量默认为final
     * */
    public static Supplier<Integer> testClosure() {
        int i = 1;
//        i++;  //匿名函数里使用外部变量必须要将变量声名为final，使用lambda会自动认为外部变量是final的。如果外边变量做修改，会提示错误。
        return () -> {
            return i; //这里会出现编译错误
        };
    }

    /**
     * 方法引用 lambda 可以代表某个接口函数中的唯一方法的匿名描述符， 也可以使用某个具体方法来代表
     * Integer::parseInt 静态方法引用
     * System.out::print 实例方法引用
     * Person::new 构造方法引用
     */
    private static void test3(){
        String[] array = new String[]{"1", "2", "3"};
        List<Integer> collect = Arrays.stream(array).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println("lambda :: 使用静态方法" + collect);

        StringNew stringNew = new StringNew();
        List<String> collect2 = Arrays.stream(array).map(stringNew::work).collect(Collectors.toList());
        System.out.println("lambda :: 使用实例方法" +collect2);

        List<String> collect3 = Arrays.stream(array).map(String::new).collect(Collectors.toList());
        System.out.println("lambda :: 使用构造方法" +collect3);
    }

    static class StringNew{
        public String work(String str){
            return str.toUpperCase() + str.toUpperCase();
        }
    }

    public static void main(String[] args) {
        test1();
//        test2();
//        test3();
    }
}

