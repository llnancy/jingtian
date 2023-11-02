package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/25
 */
public class PrintFoobarAlternately {

    interface FooBar {
        void foo(Runnable printFoo) throws Exception;
        void bar(Runnable printBar) throws Exception;
    }

    static class SemaphoreFooBar implements FooBar {
        private final int n;

        public SemaphoreFooBar(int n) {
            this.n = n;
        }

        private final Semaphore fooSem = new Semaphore(1);
        private final Semaphore barSem = new Semaphore(0);

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                fooSem.acquire();
                printFoo.run();
                barSem.release();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                barSem.acquire();
                printBar.run();
                fooSem.release();
            }
        }
    }

    static class CyclicBarrierFooBar implements FooBar {
        private final int n;

        public CyclicBarrierFooBar(int n) {
            this.n = n;
        }

        private final CyclicBarrier cb = new CyclicBarrier(2);
        volatile boolean fooExec = true;

        public void foo(Runnable printFoo) throws BrokenBarrierException, InterruptedException {
            for (int i = 0; i < n; i++) {
                while (true) {
                    if (fooExec) break;
                }
                printFoo.run();
                fooExec = false;
                cb.await();
            }
        }
        
        public void bar(Runnable printBar) throws BrokenBarrierException, InterruptedException {
            for (int i = 0; i < n; i++) {
                cb.await();
                printBar.run();
                fooExec = true;
            }
        }
    }

    static class YieldFooBar implements FooBar {
        private final int n;

        public YieldFooBar(int n) {
            this.n = n;
        }

        volatile boolean fooExec = true;

        public void foo(Runnable printFoo) {
            for (int i = 0; i < n;) {
                if (fooExec) {
                    printFoo.run();
                    fooExec = false;
                    i++;
                } else {
                    Thread.yield();
                }
            }
        }

        public void bar(Runnable printBar) {
            for (int i = 0; i < n;) {
                if (!fooExec) {
                    printBar.run();
                    fooExec = true;
                    i++;
                } else {
                    Thread.yield();
                }
            }
        }
    }

    static class ReentrantLockFooBar implements FooBar {
        private final int n;

        public ReentrantLockFooBar(int n) {
            this.n = n;
        }

        volatile boolean fooExec = true;

        ReentrantLock lock = new ReentrantLock(true);
        
        @Override
        public void foo(Runnable printFoo) throws Exception {
            for (int i = 0; i < n;) {
                lock.lock();
                try {
                    if (fooExec) {
                        printFoo.run();
                        fooExec = false;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public void bar(Runnable printBar) throws Exception {
            for (int i = 0; i < n;) {
                lock.lock();
                try {
                    if (!fooExec) {
                        printBar.run();
                        fooExec = true;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        // runSemaphoreFooBar();
        // runCyclicBarrierFooBar();
        // runYieldFooBar();
        runReentrantLockFooBar();
    }

    public static void run(FooBar fooBar) {
        new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.println("foo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.println("bar"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void runSemaphoreFooBar() {
        SemaphoreFooBar sfb = new SemaphoreFooBar(100);
        run(sfb);
    }

    public static void runCyclicBarrierFooBar() {
        CyclicBarrierFooBar cfb = new CyclicBarrierFooBar(4);
        run(cfb);
    }

    public static void runYieldFooBar() {
        YieldFooBar yfb = new YieldFooBar(4);
        run(yfb);
    }

    public static void runReentrantLockFooBar() {
        ReentrantLockFooBar rfb = new ReentrantLockFooBar(4);
        run(rfb);
    }
}
