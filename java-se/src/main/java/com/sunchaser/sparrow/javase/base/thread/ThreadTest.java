package com.sunchaser.sparrow.javase.base.thread;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author admin@lilu.org.cn
 * @since JDK8 2020/11/22
 */
public class ThreadTest {
    public static void main(String[] args) {
        // extends thread
        new MyExtendsThread().start();
        // impl runnable
        new Thread(new MyImplRunnableThread()).start();
        // impl callable
        MyCallableThread callableThread = new MyCallableThread();
        FutureTask<String> ft = new FutureTask<>(callableThread);
        new Thread(ft).start();
        try {
            String s = ft.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new MyImplRunnableThread());
        Future<String> submit = executorService.submit(callableThread);
        try {
            String s = submit.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("use Timer to create a thread.");
            }
        },1L);
        System.out.println(LocalDate.now().toString());
        System.out.println(LocalDate.now().minusDays(7).toString());
    }

    static class MyExtendsThread extends Thread {
        @Override
        public void run() {
            System.out.println("extend thread to create a thread.");
        }
    }

    static class MyImplRunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println("implements Runnable to create a thread.");
        }
    }

    static class MyCallableThread implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "implements Callable<V> to create a thread";
        }
    }
}
