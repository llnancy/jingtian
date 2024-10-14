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
            // 线程可能被虚假唤醒，所以此处使用 while 循环判断
            while (buffer.isEmpty()) {
                // isEmpty 判断为 true，如果在调用 wait 之前发生线程上下文切换
                // 此时 save 方法被执行，notify 不会产生任何效果
                // 然后线程上下文切换回来执行 wait，就会一直进行等待
                // 所以此处的 isEmpty 判断和 wait 调用整体需要和 notify 进行互斥
                wait();
            }
        }
        return buffer.remove();
    }
}
