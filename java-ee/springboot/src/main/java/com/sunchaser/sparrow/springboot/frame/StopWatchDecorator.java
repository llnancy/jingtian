package com.sunchaser.sparrow.springboot.frame;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * 装饰一下org.springframework.util.StopWatch
 *
 * 支持JDK7的try-with-resources特性：
 *
 * 1、自动回调org.springframework.util.StopWatch#stop()方法
 * 2、使用JDK7的try-with-resources特性时，如果指定了watch的id，会自动打印该id的watch耗时。
 * 3、如果配合CountDownLatch使用，会自动回调java.util.concurrent.CountDownLatch#countDown()方法
 *
 * code example：
 *
 * try (StopWatchDecorator watch = new StopWatchDecorator("stop-watch")) {
 *     // do biz
 *     // 不用在finally代码块里面调用watch.stop了。
 *     // 不用在finally代码块里面进行countDownLatch.countDown()了。
 * } catch (Exception e) {
 * }
 *
 * @author sunchaserlilu@didiglobal.com
 * @since JDK8 2021/1/30
 */
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StopWatchDecorator extends StopWatch implements AutoCloseable {

    /**
     * 可选，try-with-resources回调时进行countDown()
     */
    private CountDownLatch countDownLatch;

    /**
     * 支持try-with-resources写法
     * 1、监控停止：org.springframework.util.StopWatch#stop()
     * 2、耗时打印
     * 2、闭锁CountDownLatch减一
     * @throws Exception org.springframework.util.StopWatch#stop() throws
     */
    @Override
    public void close() throws Exception {
        if (this.isRunning()) {
            this.stop();
        }
        if (StringUtils.isNotBlank(this.getId())) {
            log.info("{}执行耗时{}ms", this.getId(), this.getTotalTimeMillis());
        }
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public StopWatchDecorator(CountDownLatch countDownLatch) {
        super();
        this.countDownLatch = countDownLatch;
    }

    public StopWatchDecorator(String id, CountDownLatch countDownLatch) {
        super(id);
        this.countDownLatch = countDownLatch;
    }

    public StopWatchDecorator() {
        super();
    }

    public StopWatchDecorator(String id) {
        super(id);
    }
}
