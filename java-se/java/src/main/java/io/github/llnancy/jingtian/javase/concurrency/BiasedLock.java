package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/28
 */
@Slf4j
public class BiasedLock {

    public static void main(String[] args) throws InterruptedException {
        Obj obj = new Obj();
        // 当可偏向对象第一次调用 hashCode 方法后，hashCode 会存入 Mark Word，就无法进入偏向锁状态了。
        obj.hashCode();
        String printable = ClassLayout.parseInstance(obj).toPrintable();
        LOGGER.info("{}", printable);

        // 偏向锁默认有延迟，添加 VM 参数 -XX:BiasedLockingStartupDelay=0 禁用延迟。
        // TimeUnit.SECONDS.sleep(4L);

        synchronized (obj) {
            LOGGER.info("{}", ClassLayout.parseInstance(obj).toPrintable());
        }

        LOGGER.info("{}", ClassLayout.parseInstance(obj).toPrintable());

        String biasedPrintable = ClassLayout.parseInstance(new Obj()).toPrintable();
        LOGGER.info("{}", biasedPrintable);

        // 添加 VM 参数 -XX:-UseBiasedLocking 禁用偏向锁
        // UseBiasedLocking 是一个开关，前面加减号 - 表示禁用开关，加加号 + 表示开启开关，默认开启。
    }
}

class Obj {
}
