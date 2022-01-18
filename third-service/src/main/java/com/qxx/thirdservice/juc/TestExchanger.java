package com.qxx.thirdservice.juc;

import java.util.concurrent.Exchanger;

public class TestExchanger {

    static  Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        // 第一个线程执行到exchange到时候会阻塞，  等第二个线程执行exchange时，会和第一个线程进行交换， 然后两个线程解除阻塞
        new Thread(() -> {
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);
        }, "t1").start();

        new Thread(() -> {
            String s = "T2";
            try {
                Thread.sleep(2000);
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);
        }, "t2").start();
    }
 }
