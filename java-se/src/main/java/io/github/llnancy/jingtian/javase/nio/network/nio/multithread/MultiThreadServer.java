package io.github.llnancy.jingtian.javase.nio.network.nio.multithread;

import io.github.llnancy.jingtian.javase.nio.ByteBuffers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * NIO多线程版 Server端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class MultiThreadServer {

    public static void main(String[] args) {
        Thread.currentThread().setName("Boss");
        try (ServerSocketChannel ssc = ServerSocketChannel.open();
             Selector boss = Selector.open()) {
            ssc.configureBlocking(false);
            ssc.register(boss, SelectionKey.OP_ACCEPT, null);
            ssc.bind(new InetSocketAddress(8080));
            final Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors() - 1];
            for (int i = 0; i < workers.length; i++) {
                workers[i] = new Worker("worker-" + i);
            }
            final AtomicInteger index = new AtomicInteger(0);
            while (true) {
                boss.select();
                Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isAcceptable()) {
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        LOGGER.debug("connected...{}", sc.getRemoteAddress());
                        LOGGER.debug("before register...{}", sc.getRemoteAddress());
                        // 轮询
                        workers[index.getAndIncrement() % workers.length].register(sc);// boss线程调用register(sc)
                        LOGGER.debug("after register...{}", sc.getRemoteAddress());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {

        private Selector selector;

        private final String name;

        private final ConcurrentLinkedQueue<Consumer<Selector>> queue = new ConcurrentLinkedQueue<>();

        private volatile boolean start = false;

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                // 先初始化selector，避免下面Thread线程中执行selector.select();空指针
                selector = Selector.open();
                new Thread(this, name).start();
                start = true;
            }
            queue.add(selector -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            // 唤醒selector.select方法，执行queue中的任务，注册读事件
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    final Consumer<Selector> task = queue.poll();
                    if (Objects.nonNull(task)) {
                        task.accept(selector);// work线程真正执行读事件的注册sc.register(selector, SelectionKey.OP_READ, null);
                    }
                    final Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        final SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            LOGGER.debug("read...{}", sc.getRemoteAddress());
                            sc.read(buffer);
                            buffer.flip();
                            ByteBuffers.debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
