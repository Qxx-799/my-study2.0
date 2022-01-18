package com.qxx.thirdservice.juc;

import java.util.ArrayList;
import java.util.List;

public class TestVolatile {

     List<Object> list = new ArrayList<>();

    public void add (Object o){
        list.add(o);
    }

    public Integer getSize(){
        return list.size();
    }
    public static void main(String[] args) {

        TestVolatile testVolatile = new TestVolatile();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                testVolatile.add(new Object());
                System.out.println("add " + i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true){
                if (testVolatile.getSize() == 5){
                    break;
                }
            }
            System.out.println("thread2线程结束");
        }).start();

    }
}
