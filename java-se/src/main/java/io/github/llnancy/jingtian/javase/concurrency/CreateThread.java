package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * create thread
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
@Slf4j
public class CreateThread {

    public static void main(String[] args) throws Exception {
        // extends Thread
        new MyExtendsThread().start();

        // impl Runnable
        new Thread(new MyImplRunnable()).start();

        // impl Callable
        FutureTask<String> ft = new FutureTask<>(new MyCallableThread());
        new Thread(ft).start();
        String res = ft.get();
        LOGGER.info("FutureTask.get(). res={}.", res);

        // ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new MyImplRunnable());
        Future<String> future = executorService.submit(new MyCallableThread());
        String futureRes = future.get();
        LOGGER.info("ExecutorService.submit(), futureRes={}.", futureRes);

        // Timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Create a thread through the Timer class.");
            }
        }, 1L);
    }

    @Slf4j
    static class MyExtendsThread extends Thread {

        @Override
        public void run() {
            LOGGER.info("Create a thread by inheriting from the Thread class.");
        }
    }

    @Slf4j
    static class MyImplRunnable implements Runnable {

        @Override
        public void run() {
            LOGGER.info("Create a thread by implementing the Runnable interface.");
        }
    }

    static class MyCallableThread implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "Create a thread by implementing the Callable interface.";
        }
    }
}
