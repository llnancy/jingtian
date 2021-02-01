package com.sunchaser.sparrow.springboot.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * 扩展org.springframework.util.StopWatch
 *
 * 可单独监控耗时工具类使用，也可配合CountDownLatchExecutorUtils工具类使用。
 *
 * 支持JDK7的try-with-resources特性：
 *
 * 1、自动回调org.springframework.util.StopWatch#stop()方法，无需手动调用stop()方法。
 * 2、指定了watch的id时，会自动打印该id的watch耗时。
 * 3、如果配合CountDownLatch使用，会自动回调java.util.concurrent.CountDownLatch#countDown()方法将闭锁减一。
 *
 * code example：
 *
 * try (StopWatchWrapper watch = new StopWatchWrapper("stop-watch")) {
 *     // 专心写业务逻辑
 *     // 1、不用在finally代码块里面调用watch.stop了。
 *     // 2、不用在finally代码块里面进行countDownLatch.countDown()了。
 *     // 3、自动打印日志：stop-watch执行耗时{}ms
 * } catch (Exception e) {
 * }
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/30
 */
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StopWatchWrapper extends StopWatch implements AutoCloseable {

    /**
     * 可选，try-with-resources回调时进行countDown()
     */
    private CountDownLatch countDownLatch;

    /**
     * 支持try-with-resources写法
     * 1、监控停止：org.springframework.util.StopWatch#stop()
     * 2、耗时打印
     * 3、闭锁CountDownLatch减一
     * @throws Exception org.springframework.util.StopWatch#stop() throws
     */
    @Override
    public void close() throws Exception {
        if (this.isRunning()) {
            this.stop();
        }
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        if (StringUtils.isNotBlank(this.getId())) {
            log.info("{}-执行耗时{}ms", this.getId(), this.getTotalTimeMillis());
        }
    }

    public StopWatchWrapper(CountDownLatch countDownLatch) {
        super();
        this.countDownLatch = countDownLatch;
    }

    public StopWatchWrapper(String id, CountDownLatch countDownLatch) {
        super(id);
        this.countDownLatch = countDownLatch;
    }

    public StopWatchWrapper() {
        super();
    }

    public StopWatchWrapper(String id) {
        super(id);
    }
}
