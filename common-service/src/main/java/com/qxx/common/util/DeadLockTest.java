package com.qxx.common.util;

public class DeadLockTest {

    private static Object obj1 = new Object();
    private static Object obj2 = new Object();

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (obj1){
                try {
                    System.out.println("thread1-begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2){
                    System.out.println("thread1-end");
                }
            }
        }).start();

        new Thread(()->{
            synchronized (obj2){
                try {
                    System.out.println("thread2-begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1){
                    System.out.println("thread2-end");
                }
            }
        }).start();

    }
}
