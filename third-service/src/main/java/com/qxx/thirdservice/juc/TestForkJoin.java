package com.qxx.thirdservice.juc;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.*;

public class TestForkJoin {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Integer> submit = executorService.submit(() -> {
            System.out.println("runnable..");
            return 1;
        });

        Integer integer = submit.get(2, TimeUnit.SECONDS);

        System.out.println(integer);


        int[] arr = new int[10000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        int numThread = args.length / 10000 > 0 ? arr.length / 10000 : 1;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ForkJoinTask<Object>() {

            @Override
            public Object getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Object value) {

            }

            @Override
            protected boolean exec() {
                return false;
            }
        });

    }
}
