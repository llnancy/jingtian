package io.github.llnancy.jingtian.javase.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁和非公平锁
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/9
 */
public class FairAndUnfair {

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread " + i);
        }
        for (Thread thread : threads) {
            thread.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Job implements Runnable {

        private final PrintQueue printQueue;

        public Job(PrintQueue printQueue) {
            this.printQueue = printQueue;
        }

        @Override
        public void run() {
            /*
             子线程中不能使用 Logger 日志框架打印，否则顺序会错乱。
             System.out.printf 加了锁保证打印顺序。
             */
            System.out.printf("%s: Going to print a job.\n", Thread.currentThread().getName());
            printQueue.printJob();
            System.out.printf("%s: The job has been printed.\n", Thread.currentThread().getName());
        }
    }

    static class PrintQueue {

        /**
         * 通过改变 ReentrantLock 构造函数的参数为 true 或 false 来设置公平锁和非公平锁
         */
        private final Lock queueLock = new ReentrantLock(true);

        public void printJob() {
            doPrintJob();
            doPrintJob();
        }

        private void doPrintJob() {
            queueLock.lock();
            try {
                long duration = (long) (Math.random() * 10000);
                System.out.printf("%s: PrintQueue: Printing a Job during %d seconds.\n", Thread.currentThread().getName(), duration / 1000);
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                queueLock.unlock();
            }
        }
    }
}
