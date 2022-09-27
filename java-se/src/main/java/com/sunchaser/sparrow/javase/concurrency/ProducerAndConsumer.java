package com.sunchaser.sparrow.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * producer and consumer
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
@Slf4j
public class ProducerAndConsumer {

    public static void main(String[] args) {
        // baseBlockingQueue();
    }

    static class MyBlockingQueueBaseWaitNotify<T> {

        private final Queue<T> queue;

        private final int capacity;

        public MyBlockingQueueBaseWaitNotify() {
            this(10);
        }

        public MyBlockingQueueBaseWaitNotify(int capacity) {
            this.capacity = capacity;
            queue = new LinkedList<>();
        }

        public void put(T data) throws InterruptedException {
            synchronized (this) {
                while (queue.size() == capacity) {
                    // queue已满，wait进行阻塞等待消费者消费数据
                    wait();
                }
                // 入队
                queue.add(data);
                // 通知唤醒所有正在阻塞等待的消费者
                notifyAll();
            }
        }

        public T get() throws InterruptedException {
            synchronized (this) {
                while (queue.size() == 0) {
                    // queue已空，wait进行阻塞等待生产者生产数据
                    wait();
                }
                // 出队
                T data = queue.remove();
                // 通知唤醒所有正在阻塞等待的生产者
                notifyAll();
                return data;
            }
        }
    }

    static class MyBlockingQueueBaseCondition<T> {

        private final Queue<T> queue;

        private final int capacity;

        private final Lock lock = new ReentrantLock();

        /**
         * 非空
         */
        private final Condition notEmpty = lock.newCondition();

        /**
         * 非满
         */
        private final Condition notFull = lock.newCondition();

        public MyBlockingQueueBaseCondition() {
            this(10);
        }

        public MyBlockingQueueBaseCondition(int capacity) {
            this.capacity = capacity;
            this.queue = new LinkedList<>();
        }

        public void put(T data) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == capacity) {
                    // queue已满，await进行阻塞等待消费者消费数据
                    notFull.await();
                }
                // 入队，队列非空
                queue.add(data);
                // 通知唤醒所有正在阻塞等待的消费者
                notEmpty.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public T take() throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == 0) {
                    // queue已空，await进行阻塞等待生产者生产数据
                    notEmpty.await();
                }
                // 出队，队列非满
                T data = queue.remove();
                // 通知唤醒所有正在阻塞等待的生产者
                notFull.signalAll();
                return data;
            } finally {
                lock.unlock();
            }
        }
    }

    private static void baseBlockingQueue() {
        // 阻塞队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        // 生产者
        Runnable producer = () -> {
            while (true) {
                try {
                    queue.put(UUID.randomUUID().toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        // 启动生产者
        new Thread(producer).start();
        new Thread(producer).start();
        // 消费者
        Runnable consumer = () -> {
            while (true) {
                try {
                    String take = queue.take();
                    LOGGER.info("consumer take result is {}.", take);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        // 启动消费者
        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}
