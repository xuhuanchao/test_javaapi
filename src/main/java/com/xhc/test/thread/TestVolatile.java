package com.xhc.test.thread;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * volatile 可以保证变量的可见性，可以禁止指令重排保证了一定的顺序性
 * volatile将store和write操作合并为一个原子操作，保证结果直接反应到主内存中
 * volatile 对复合操作不能保证原子性，比如自增++操作，是分读、写2步。
 * volatile 禁止指令重排，volatile 之前的指令不会排到volatile之后，volatile之后的指令不会排到volatile之前。
 *
 * volatile 常用于状态变量
 *
 * jvm内存规范了内存交互的操作：
 * Lock(锁定)：作用于主内存中的变量，把一个变量标识为一条线程独占的状态。
 * Read(读取)：作用于主内存中的变量，把一个变量的值从主内存传输到线程的工作内存中。
 * Load(加载)：作用于工作内存中的变量，把read操作从主内存中得到的变量的值放入工作内存的变量副本中。
 * Use(使用)：作用于工作内存中的变量，把工作内存中一个变量的值传递给执行引擎。
 * Assign(赋值)：作用于工作内存中的变量，把一个从执行引擎接收到的值赋值给工作内存中的变量。
 * Store(存储)：作用于工作内存中的变量，把工作内存中的一个变量的值传送到主内存中。
 * Write(写入)：作用于主内存中的变量，把store操作从工作内存中得到的变量的值放入主内存的变量中。
 * Unlock(解锁)：作用于主内存中的变量，把一个处于锁定状态的变量释放出来，之后可被其它线程锁定。
 *
 *
 *
 * {@link #testAtom()} 测试volatile的原子性
 * {@link #testReorder()} 测试代码重排  todo:没有成功
 * {@link #testVolatileVisibility()} 测试可见性  todo:没有成功
 *
 * @Author: xhc
 * @Date: 2020/4/12 10:34
 * @Description:
 */
@Data
public class TestVolatile {


    private volatile Integer n = 0;

    //需要用 volatile  修饰状态标记量，禁止指令重排和更新主内存
    private boolean flag = true;


    //需要用 volatile  修饰状态标记量，禁止指令重排同时更新主内存
    private int context = 0;
    private boolean inited = false;


    /**
     * 不能保证自增为原型性
     * 循环1000遍后，n的值不是1000
     */
    public void testAtom(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                n = ++n;
                System.out.println("n=" + n);
            }
        };

        for(int i=0; i<1000; i++){
            Thread t = new Thread(r);
            t.start();;
        }
    }


    /**
     *  测试代码重排，在没有使用volatile的情况下，没有前后依赖的代码可能被重排颠倒了执行顺序
     * context = 10;
     * inited = true;
     * 有可能顺序颠倒
     *
     * todo:网上给出的代码重排例子，但是没有试验出来不加volatile会导致代码重排情况
     */
    public void testReorder(){
        Thread t1 = new Thread(() -> {
            context = 10;
            inited = true;
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while(!inited){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(inited){
                System.out.println(Thread.currentThread().getName() + "==> 10 / " + context + " = " + 10 / context);
            }else{
                System.out.println(Thread.currentThread().getName() + "===> t1 not inited");

            }



        });
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试volatile修饰变量提供可见性，对变量的修改会马上反映到主内存
     *
     * 如果不使用volatilex 修饰状态变量 flag，可能 t1 的循环不会结束。
     * 因为 t2 对flag的修改 可能没有立刻反映到主内存，或t1 不知道flag修改了没有重读flag的值
     *
     * todo: 没有试验出来不加volatile，flag被修改后，其他线程不能读到的情况
     */
    public void testVolatileVisibility(){
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int i = 0;
            while (flag ) {
                i++;
                if(i % 100 == 0){
                    System.out.println(name + " do something...");
                }
            }
            System.out.println(name + "停止了");
        };


        Runnable r2 = () -> {
            String name = Thread.currentThread().getName();
            try {
                TimeUnit.SECONDS.sleep(2);
                flag = false;
                System.out.println(name + " set flag = false");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r1);
        executorService.execute(r2);
        executorService.shutdown();



    }


    public static void main(String[] args) {
        TestVolatile testVolatile = new TestVolatile();

        //测试 volatile 不能提供原子性操作
//        testVolatile.testAtom();

        //测试 volatile 限制重排
//        for(int i=0; i<1000; i++){
//            TestVolatile testVolatile2 = new TestVolatile();
//            testVolatile2.testReorder();
//        }

        //测试
        testVolatile.testVolatileVisibility();


    }

}
