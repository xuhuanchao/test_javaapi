package com.xhc.test.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * volatile 只能保证变量发布安全，确保修改变量后直接反映到主内存，不能保证并发安全。
 *
 *
 * @Author: magicxu
 * @Date: 2020/3/17 12:27
 * @Description:
 */
public class VolatileTest {

    private volatile int a = 0;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();

        CountDownLatch countDownLatch = new CountDownLatch(10);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                int a = volatileTest.getA();
                System.out.println(name + "获取到a="+a);
                volatileTest.setA(a+1);
                countDownLatch.countDown();
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();

        try {
            countDownLatch.await();
            System.out.println("最终a的结果=" + volatileTest.getA());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
