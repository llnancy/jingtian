package io.github.llnancy.jingtian.javase.concurrency;

import java.util.LinkedList;
import java.util.Queue;

/**
 * wait and notify
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
public class WaitNotify {

    private final Queue<String> buffer = new LinkedList<>();

    public void save(String data) {
        synchronized (this) {
            buffer.add(data);
            notify();
        }
    }

    public String take() throws InterruptedException {
        synchronized (this) {
            // 线程可能被虚假唤醒，所以此处使用while循环判断
            while (buffer.isEmpty()) {
                // isEmpty判断为true，如果在调用wait之前发生线程上下文切换
                // 此时save方法被执行，notify不会产生任何效果
                // 然后线程上下文切换回来执行wait，就会一直进行等待
                // 所以此处的isEmpty判断和wait调用整体需要和notify进行互斥
                wait();
            }
        }
        return buffer.remove();
    }
}
