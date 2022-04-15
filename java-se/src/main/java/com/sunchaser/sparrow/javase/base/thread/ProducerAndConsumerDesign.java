package com.sunchaser.sparrow.javase.base.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/7/7
 */
public class ProducerAndConsumerDesign {
    public static void main(String[] args) {
        /**
        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Runnable producer = () -> {
            while (true) {
                try {
                    queue.put("producer");
                    System.out.println("producer put");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(producer).start();
        new Thread(producer).start();
        Runnable consumer = () -> {
            while (true) {
                try {
                    queue.take();
                    System.out.println("consumer take");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(consumer).start();
        new Thread(consumer).start();
         **/

    }
}
