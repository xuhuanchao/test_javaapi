package com.xhc.test.thread;


import java.util.concurrent.TimeUnit;


/**
 * 测试线程阻塞状态不能响应中断。
 * t1获得锁后，t2等待t1释放锁时，执行t2.interrupt();
 * t2线程在等待锁blocked时不能响应interrupt()中断。获取锁后才可以
 *
 */
public class InterruptedTest {

    private Integer number = 0;

    public synchronized void lockAdd(Integer number) throws Exception {
        try {
            String name = Thread.currentThread().getName();
            this.number += number;
            TimeUnit.SECONDS.sleep(10);
            System.out.println(name + "释放了锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static void main(String[] args) {
        InterruptedTest test = new InterruptedTest();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                try {
                    test.lockAdd(1);
                } catch (Exception e) {
                    System.out.println(name + " 捕获到异常：" + e.getMessage());
                }
            }
        };


        Thread t1 = new Thread(runnable, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(runnable, "t2");
        t2.start();

        System.out.println("t1状态：" + t1.getState() + ", t2状态：" + t2.getState());

        // 由于同步代码会占用10秒时间，t2在等待锁block时，执行中断是不能响应的。
        t2.interrupt();
        System.out.println("执行t2的 interrupt()方法");
    }

}
