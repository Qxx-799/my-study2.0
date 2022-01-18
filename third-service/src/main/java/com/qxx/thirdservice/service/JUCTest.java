package com.qxx.thirdservice.service;

import com.qxx.thirdservice.util.QxxLock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class JUCTest {

    public int m = 0;
    public static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        JUCTest jucTest = new JUCTest();
        jucTest.test();

    }

    public void test() throws InterruptedException {
        QxxLock qxxLock = new QxxLock();
        new Thread(()->{
            try {
                latch.await();
                qxxLock.lock();
                System.out.println("thread1获取到锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 1000; i++) {
                m = m+1;
            }
            try {
                qxxLock.release();
                System.out.println("thread1释放锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                latch.await();
                qxxLock.lock();
                System.out.println("thread2获取到锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 1000; i++) {
                m = m+1;
            }
            try {
                qxxLock.release();
                System.out.println("thread2释放锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                latch.await();
                qxxLock.lock();
                System.out.println("thread3获取到锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 1000; i++) {
                m = m+1;
            }
            try {
                qxxLock.release();
                System.out.println("thread3释放锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();



        Thread.sleep(2000);

        latch.countDown();

        Thread.sleep(2000);
        System.out.println(m);

    }
}
