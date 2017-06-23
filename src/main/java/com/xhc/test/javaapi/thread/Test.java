package com.xhc.test.javaapi.thread;

public class Test {

    /**
     * 测试系统是否使用了时间片 调度线程
     */
    public static void testIsSysUseTimeSlice() {
        new ShowThread("Foo").start();
        new ShowThread("Bar").start();
    }
    
    static class ShowThread extends Thread {
        String message;
        ShowThread (String message){
            this.message = message;
        }
        public void run() {
            while(true){
                System.out.println(message);
            }
        }
    }
    
    /**
     * 得到当前线程组 的所有线程
     */
    public static void getCurrentThreads() {
        Thread[] threads = new Thread[64];
        int num = Thread.enumerate(threads);
        for(int i=0; i <num; i++){
            System.out.println(threads[i] + ":" + threads[i].getState());
        }
    }
    
    
    public static void testDefaultExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler(){
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.err.println(t + " threw exception: " + e);
                    }
                });
        new Thread( 
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("test thread running.");
                        int a = 1/0;
                    }
                }).start();
    }
    
    public static void main(String[] args) {
        testDefaultExceptionHandler();
    }
}
