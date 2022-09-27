package com.sunchaser.sparrow.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * volatile error stop
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
@Slf4j
public class VolatileErrorStop {

    @Slf4j
    static class Producer implements Runnable {

        private volatile boolean canceled = false;

        private final BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int count = 0;
            try {
                while (!canceled && count < 100000) {
                    if (count % 10 == 0) {
                        queue.put(count);
                        LOGGER.info("count = {} put queue.", count);
                    }
                    count++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                LOGGER.info("Producer finished.");
            }
        }
    }

    static class Consumer {

        private final BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public boolean needMore() {
            return Math.random() < 0.95;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        // 休眠500ms让Producer将queue放满，此时Producer阻塞在put方法
        Thread.sleep(500L);
        Consumer consumer = new Consumer(queue);
        while (consumer.needMore()) {
            // 消费queue后Producer停止阻塞
            LOGGER.info("消费了 {}.", consumer.queue.take());
            // sleep控制消费速度
            Thread.sleep(100L);
        }
        LOGGER.info("消费者不再需要数据");
        // 尝试停止生产者
        producer.canceled = true;
        // 此时Producer可能阻塞在put方法，无法停止。正确做法是使用interrupt进行中断，因为阻塞状态仍能感知中断信号。
        LOGGER.info("producer.canceled: {}", producer.canceled);
    }
}
