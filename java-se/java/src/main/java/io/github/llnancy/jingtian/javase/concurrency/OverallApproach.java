package io.github.llnancy.jingtian.javase.concurrency;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 华罗庚《统筹方法》
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/17
 */
@Slf4j
public class OverallApproach {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LOGGER.info("洗水壶");
            ThreadUtil.sleep(1000L);
            LOGGER.info("烧开水");
            ThreadUtil.sleep(15000L);
        }, "t1");

        Thread t2 = new Thread(() -> {
            LOGGER.info("洗茶壶");
            ThreadUtil.sleep(1000L);
            LOGGER.info("洗茶杯");
            ThreadUtil.sleep(2000L);
            LOGGER.info("拿茶叶");
            ThreadUtil.sleep(1000L);
            // 等待 t1 执行完成
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOGGER.info("泡茶");
        }, "t2");

        t1.start();
        t2.start();
    }
}
