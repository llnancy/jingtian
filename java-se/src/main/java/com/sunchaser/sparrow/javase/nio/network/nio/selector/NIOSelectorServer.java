package com.sunchaser.sparrow.javase.nio.network.nio.selector;

import com.sunchaser.sparrow.javase.nio.ByteBuffers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO Selector模式 Server端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/13
 */
@Slf4j
public class NIOSelectorServer {

    public static void main(String[] args) {
        try (Selector selector = Selector.open()) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            // 将ServerSocketChannel注册到Selector上，并设置关注的事件类型
            SelectionKey sscKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
            LOGGER.debug("register key: {}", sscKey);
            ssc.bind(new InetSocketAddress(8080));
            int i = 0;
            while (i < 10) {
                i++;
                // select()方法：
                // 没有事件发生时，线程阻塞；有事件到来时，线程恢复运行。
                // 事件要么进行处理，要么进行取消。如果不管事件，线程非阻塞，下次select仍会触发该事件（nio底层是水平触发）。
                // 返回值为就绪事件的数量
                int readyCount = selector.select();
                LOGGER.debug("selector readyCount={}", readyCount);
                // selectedKeys()方法：获取所有发生了事件的sscKey
                Set<SelectionKey> sscKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = sscKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    LOGGER.debug("key: {}", key);
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        // 处理事件
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        LOGGER.debug("sc: {}", sc);
                        // doAccept(selector, key);
                    } else if (key.isReadable()) {
                        // doRead(key);
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        channel.read(buffer);
                        buffer.flip();
                        ByteBuffers.debugRead(buffer);
                    }
                    // 取消事件
                    // key.cancel();
                    // key必须进行处理，且处理完后必须从集合中删除。
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        channel.read(buffer);
        buffer.flip();
        ByteBuffers.debugRead(buffer);
    }

    private static void doAccept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        // 处理事件
        SocketChannel sc = channel.accept();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        LOGGER.debug("sc: {}", sc);
    }
}
