package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

/**
 * 守护线程：只要其它非守护线程都运行结束了，即使守护线程的代码没有执行完，也会强制结束。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/17
 */
@Slf4j
public class DaemonThread {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            LOGGER.info("{} run end.", Thread.currentThread().getName());
        }, "daemon-thread");
        thread.setDaemon(true);
        thread.start();
        LOGGER.info("{} run end.", Thread.currentThread().getName());
    }
}
