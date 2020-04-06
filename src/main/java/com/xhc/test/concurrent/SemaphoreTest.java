package com.xhc.test.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * 信号量，用来限制执行线程的个数。只有拿到信号的线程才能运行，否则进入等待
 *
 * Semaphore初始化时设定最大线程个数，可以设定使用公平和非公平方式，默认非公平方式
 * 非公平方式每次acquire() 都有可能插队获取到信号量，没获取到再排队。
 * 公平方式必须要排队。
 *
 * acquire() 申请信号量，没有申请到将阻塞
 * release() 释放信号量
 *
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        //模拟借书，总共5本，没有时就等待 ，公平锁
        Semaphore semaphore = new Semaphore(5, true);


        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                Random random = new Random();
                //随机等待1-3秒，再借
                int i = random.nextInt(3) +1;
                try {
                    TimeUnit.SECONDS.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + "来借书了");
                try {
                    System.out.println("有" + semaphore.getQueueLength() + "人排队");

                    semaphore.acquire();
                    System.out.println(name + "借到书了" + 1 + "本，还剩" + semaphore.availablePermits() + "本");

                    //随机借1-10秒后归还
                    int j = random.nextInt(10) + 1;
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println(name + "借书中断了");
                } finally {
                    semaphore.release();
                    System.out.println(name + "归还了书，还剩" + semaphore.availablePermits() + "本，有" + semaphore.getQueueLength() + "人排队");
                }

            }
        };

        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.submit(runnable);
        executor.shutdown();

    }
}
