package com.xhc.test.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用来使线程等待条件满足时才进行后续操作的情形
 *
 * CountDownLatch 初始化设定计数器初始值n，可理解为当计数器值变为0时表示满足条件，一共有n个条件
 * 线程调用{@link CountDownLatch#await()}后，将阻塞等待计数器变为0时才可以继续执行。
 * 调用{@link CountDownLatch#countDown()}，可以使计数器-1
 */
public class CountDownLatchTest {

    private static final CountDownLatch countDownLatch = new CountDownLatch(5);


    public static void main(String[] args) {
        String t1Name = "主线程";
        Thread t1 = new Thread(() -> {

            System.out.println(t1Name + "开始执行。。。");
            try {
                System.out.println(t1Name + "等待await");
                System.out.println("当前countDown计数" + countDownLatch.getCount());
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(t1Name + "线程中断");
            }
            System.out.println(t1Name + "继续执行...");

        }, t1Name);
        t1.start();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始执行。。。");
                try {
                    Random random = new Random();

                    Thread.sleep(random.nextInt(3000) );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " 让countDown -1，当前计数为：" + countDownLatch.getCount());
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();
    }

}
