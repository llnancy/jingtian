package io.github.llnancy.jingtian.javase.concurrency;

/**
 * synchronized
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/20
 */
public class Synchronization {

    /**
     * monitorenter
     * monitorexit
     */
    public void fun() {
        synchronized (this) {
            System.out.println("this is a synchronized code block.");
        }
    }

    /**
     * flags: ACC_PUBLIC, ACC_SYNCHRONIZED
     */
    public synchronized void syncFun() {
        System.out.println("this is a synchronized function.");
    }
}
