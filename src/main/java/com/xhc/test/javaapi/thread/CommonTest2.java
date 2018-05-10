package com.xhc.test.javaapi.thread;

public class CommonTest2 {

    private String name = "张三";
    
    static String address = "北京";
    
    
    class TestThread extends Thread {
        String email = "123@sina.com";

        public TestThread(String email) {
            super();
            this.email = email;
        }
        @Override
        public void run() {
            name = "李四";
            address = "上海";
            System.out.println("name=" + name + ", address=" + address + ", email=" + email);
        }
        
    }
    
    public void test2() {
        new TestThread("ttt@sina.com").start();
    }
    
    public void test() {
        new Thread(new Runnable() {
            String email = "222@sohu.com";
            
            public void run() {
                name= "lisi";
                address = "天津";
                email = "333@sohu.com";
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("name=" + name + ", address=" + address + ", email=" + email);
            }
        }).start();
        
    }
    
    
    public static void main(String[] args) {
        CommonTest2 t =  new CommonTest2();
        t.test();
        t.test2();

        System.out.println("test");
    }

}
