package com.qxx.thirdservice.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestUtil {

    public static synchronized void get(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======");
    }

    public synchronized void test(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======");
    }


    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(9,()-> System.out.println("满人，发车"));

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("线程: " + Thread.currentThread().getName());
            }).start();
        }


    }
}
