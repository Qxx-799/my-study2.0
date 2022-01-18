package com.qxx.thirdservice.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class TestPhaser {

    static MarriagePhaser phaser = new MarriagePhaser();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new DoSomeThing(phaser,"线程1"));
        executorService.submit(new DoSomeThing(phaser,"线程2"));
        executorService.submit(new DoSomeThing(phaser,"线程3"));
        executorService.submit(new DoSomeThing(phaser,"线程4"));
        executorService.shutdown();

    }


    public static class MarriagePhaser extends Phaser{

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了！");
                    return false;
                case 1:
                    System.out.println("所有人吃完了！");
                    return false;
                case 2:
                    System.out.println("所有人离开了！");
                    return true;
                default:
                    return true;
            }
        }
    }

    static class DoSomeThing implements Runnable{

        private Phaser phaser;
        private String name;

        @Override
        public void run() {
            System.out.println(name + "到达现场");
            millisSecondSleep(2000);
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + "吃完了");
            millisSecondSleep(2000);
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + "离开了");
            millisSecondSleep(2000);
            phaser.arriveAndAwaitAdvance();
        }

        public DoSomeThing(Phaser phaser, String name){
            this.phaser = phaser;
            this.name = name;
            phaser.register();
        }

        public void millisSecondSleep(int millisSecond){
            try {
                Thread.sleep(millisSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
