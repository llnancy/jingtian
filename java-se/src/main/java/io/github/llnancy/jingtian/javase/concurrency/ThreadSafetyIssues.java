package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

/**
 * Thread safety issues
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/18
 */
@Slf4j
public class ThreadSafetyIssues {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        int loopCounts = 3000;
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < loopCounts; i++) {
                synchronized (ThreadSafetyIssues.class) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < loopCounts; i++) {
                synchronized (ThreadSafetyIssues.class) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        LOGGER.info("count: {}", count);
    }
}
