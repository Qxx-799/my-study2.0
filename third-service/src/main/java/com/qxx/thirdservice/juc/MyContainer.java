package com.qxx.thirdservice.juc;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyContainer<T> {

    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 20;
    private int count = 0;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t){
        try {
            lock.lock();
            while (list.size() == MAX){
                producer.await();
            }
            list.add(t);
            ++count;
            consumer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public T get(){
        T t = null;
        try {
            lock.lock();
            while (list.size() == 0){
                consumer.await();
            }
            t = list.removeFirst();
            count --;
            producer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        MyContainer<String> container = new MyContainer<>();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0 ;j< 5; j++) System.out.println(container.get());
            },"consumer" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) container.put(Thread.currentThread().getName());
            },"producer" + i).start();
        }
    }
}
