package com.qxx.thirdservice.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class QxxLock {

    private AtomicInteger stat = new AtomicInteger(0);
    private Queue<Thread> queue = new LinkedList<>();

    public boolean lock() throws InterruptedException {

        for (;;) {
            if (stat.compareAndSet(0,1)){
                return true;
            }else {
                Thread thread = Thread.currentThread();
                queue.add(thread);
                LockSupport.park();
            }
        }
    }

    public boolean release() throws InterruptedException {
        stat.set(0);
        Thread take = queue.poll();
        if (take != null){
            LockSupport.unpark(take);
        }
        return true;
    }
}
