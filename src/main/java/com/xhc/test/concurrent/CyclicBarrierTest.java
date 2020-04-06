package com.xhc.test.concurrent;

import java.util.Random;
import java.util.concurrent.*;


/**
 * 指定若干线程都要到达某一状态时再各自继续执行，否则就要等待其他线程达到这个状态
 *
 * CyclicBarrier初始化时设定参与线程数量，所有线程达到栅栏时，再一起放行
 * await() 使当前线程到达栅栏，并阻塞并等到全部线程到达后，方可继续运行。
 * await() 可以设置等待时间，如果超时，将抛出TimeoutException，同时栅栏打破，然后复位，其他await()的线程会收到BrokenBarrierException
 * 如果全部线程到达栅栏，那么栅栏也将复位，可以重新使用。
 *
 */
public class CyclicBarrierTest {

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        System.out.println("全部到齐，通过栅栏，栅栏将复位");
        testCyclicBarrier();
    });


    public void testCyclicBarrier(){
        System.out.println("乱斗大赛准备开始，等运动员集合齐后正式进行比赛。");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Runnable r1 = new Runnable() {
            @Override
            public void run(){
                String name = Thread.currentThread().getName();
                try {
                    Random random = new Random();
                    //生成0-4随机数， 0直接中断，1-4 会睡眠1-4秒
                    int i = random.nextInt(5);
                    System.out.println(name + "正在赶来，随机数：" + i);
                    if(i == 0){
                        System.out.println(name + "出现问题不能报道...");
                        throw new InterruptedException(name + "出现问题不能报道...");
                    }else{
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(name + "报道...");
                    }

                    //最大等待3秒，如果有线程中断其他线程将永远等待，还是给出等待时间更合适
                    cyclicBarrier.await(3l, TimeUnit.SECONDS);
//                    cyclicBarrier.await();

                    System.out.println(name + "开始干活了。。。");
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println(name + "线程中断了...");
                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
                    System.out.println(name + "栅栏打破了...");
                } catch (TimeoutException e) {
//                    e.printStackTrace();
                    System.out.println(name + "等待超时了...");
                }


            }
        };
        executor.submit(r1);
        executor.submit(r1);
        executor.submit(r1);
        executor.submit(r1);
        executor.submit(r1);

        executor.shutdown();
    }


    public static void main(String[] args) {
        CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest();
        cyclicBarrierTest.testCyclicBarrier();
//        cyclicBarrierTest.executor.shutdown();
    }
}
