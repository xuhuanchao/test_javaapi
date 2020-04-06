package com.xhc.test.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;


/**
 * java 8 引入
 * 支持乐观读、悲观读、写锁。
 *
 * 悲观读和写锁不可重入，乐观锁也会返回0。可以读写锁互换，不支持Condition
 *
 * 1. 测试写锁、乐观读、悲观读 的使用 {@link #testStampledLock()}
 * 2. 测试重入 {@link #testNoAccessAgain()} {@link #testNoAccessAgain2()}
 */
public class StampedLockTest {

    private StampedLock stampedLock = new StampedLock();
    private Integer x = 0;


    /**
     * 后去写锁，写入
     * @param y
     */
    public void add(Integer y){
        long stamp = stampedLock.writeLock();
        try {
            x = x+y;
            System.out.println(Thread.currentThread().getName() + "写入操作x=" + x);
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    /**
     * 读数据， 使用乐观读，如果乐观读到的数据不正确，可以再使用悲观读
     * @param usePessimism  是否使用悲观读
     * @return
     */
    public Integer readInteger(boolean usePessimism){
        String name = Thread.currentThread().getName();
        Integer result = null;
        long stamp = stampedLock.tryOptimisticRead();
        result = x;


        if(!stampedLock.validate(stamp) && usePessimism){
//            System.out.println(name + "触发悲观读");
            stamp = stampedLock.readLock();

            try {
                result = x;
                System.out.println(name + "悲观读x=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }else{
            System.out.println(name + "乐观读x=" + result);
        }
        return result;
    }


    /**
     * 创建线程池，加入10个任务，有写有读，使用栅栏并发执行线程
     * 结果：可能有读线程使用了乐观读
     */
    public static void testStampledLock(){
        StampedLockTest stampedLockTest = new StampedLockTest();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                    stampedLockTest.add(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                    stampedLockTest.readInteger(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(runnable);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);
        executor.submit(runnable2);

        executor.shutdown();
    }


    /**
     * 测试不可重入
     *
     * 获取了悲观读，可以再获取悲观读和乐观读锁，获取写锁需要等待
     */
    public static void testNoAccessAgain(){
        StampedLock stampedLock = new StampedLock();

        //获取悲观读锁
        long readLock = stampedLock.readLock();
        System.out.println("获得悲观读锁 :" + readLock);

        //获取乐观读锁
        long optimisticReadLock = stampedLock.tryOptimisticRead();
        System.out.println("获得乐观读锁 :" + optimisticReadLock);

        //获取悲观读锁
        long readLock2 = stampedLock.readLock();
        System.out.println("获得悲观读锁第二次 :" + readLock2);

        //获取写锁，会阻塞等待
        long writeLock = stampedLock.writeLock();
        System.out.println("获得写锁 :" + writeLock);

        System.out.println("运行结束。");
    }


    /**
     * 测试不可重入
     *
     * 获取写锁后，不能再获取写锁和悲观读锁，获取乐观读锁会返回0表示资源被独占
     */
    public static void testNoAccessAgain2(){
        StampedLock stampedLock = new StampedLock();

        long writeLock = stampedLock.writeLock();
        System.out.println("获得写锁 :" + writeLock);

        //获取乐观读锁，等到0
        long optimisticReadLock = stampedLock.tryOptimisticRead();
        System.out.println("获得乐观读锁 :" + optimisticReadLock);

        //获取悲观读锁，会阻塞
        long readLock = stampedLock.readLock();
        System.out.println("获得悲观读锁 :" + readLock);

        //会阻塞
        long writeLock2 = stampedLock.writeLock();
        System.out.println("获得写锁第二次 :" + writeLock2);
    }

    public static void main(String[] args) {
//        testStampledLock();
//        testNoAccessAgain();
        testNoAccessAgain2();
    }
}

