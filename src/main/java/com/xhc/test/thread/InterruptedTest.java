package com.xhc.test.thread;


import java.util.concurrent.TimeUnit;


/**
 *
 * t1获得锁后，t2等待锁时，执行t2.interrupt();
 * t2线程在等待锁block时不能响应中断。获取锁后才可以
 *
 */
public class InterruptedTest {

    private Integer number = 0;

    public synchronized void lockAdd(Integer number){
        String name = Thread.currentThread().getName();
        this.number += number;
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println(name + "释放了锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(name + "中断了");
        }
    }


    public static void main(String[] args) {
        InterruptedTest test = new InterruptedTest();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                try {
                    if(!Thread.interrupted()){
                        test.lockAdd(1);
                    }else{
                        System.out.println(name + "检测到中断");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        Thread t1 = new Thread(runnable, "t1");
        t1.start();

        Thread t2 = new Thread(runnable, "t2");
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }

}
