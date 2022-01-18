package com.qxx.thirdservice.juc;

import com.qxx.thirdservice.entity.DemandRequest;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TestCyclicBarrier {


    public static void main(String[] args) {
        /** 当到达设置当11个线程数时， 才会继续往下执行 */
        CyclicBarrier barrier = new CyclicBarrier(11, () -> System.out.println("人满，发车！"));
        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程: " + Thread.currentThread().getName());
            }).start();
        }

    }

}
