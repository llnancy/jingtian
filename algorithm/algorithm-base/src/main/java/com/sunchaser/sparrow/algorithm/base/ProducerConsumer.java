package com.sunchaser.sparrow.algorithm.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/26
 */
public class ProducerConsumer {
    private static final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
    private volatile boolean flag = true;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    public void produce() throws InterruptedException {
        while (flag) {
            boolean offer = blockingQueue.offer(atomicInteger.incrementAndGet(), 2, TimeUnit.SECONDS);
            System.out.println("produce:" + offer);
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            Integer poll = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (poll == null) {
                return;
            }
            System.out.println("consume:" + poll);
        }
    }

    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        pc.stop();
    }
}
