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
            while (true) {
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
                    // key必须进行处理，且处理完后必须从集合中删除。
                    iter.remove();
                    if (key.isAcceptable()) {
                        doAccept(selector, key);
                    } else if (key.isReadable()) {
                        // 客户端主动断开连接时，为了让服务端感知到断开，会触发OP_READ读事件，从channel中读数据会返回-1。
                        doRead(key);
                    }
                    // 取消事件
                    // key.cancel();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doAccept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        // 处理事件
        SocketChannel sc = channel.accept();
        sc.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(16);// attachment
        // 将一个ByteBuffer作为attachment附件关联到selectionKey上
        sc.register(selector, SelectionKey.OP_READ, buffer);
        LOGGER.debug("sc: {}", sc);
    }

    private static void doRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 获取selectionKey上关联的附件
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        int read = channel.read(buffer);
        if (read < 0) {// 无数据可读时返回0，小于0说明客户端断开连接了。
            // 关闭channel
            channel.close();
            // 取消事件
            key.cancel();
            return;
        }
        split(buffer);
        // compact压缩之后无变化说明没有以\n结尾的字节，此buffer不是一条完整的消息，需要进行扩容
        if (buffer.position() == buffer.limit()) {
            // 扩容2倍
            ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
            buffer.flip();
            // 将旧buffer内容填入扩容后的新buffer
            newBuffer.put(buffer);
            // 将扩容后的newBuffer作为附件与selectionKey进行关联
            key.attach(newBuffer);
        }
    }

    /**
     * 消息以\n分隔时
     * 拆分出一条完整消息
     *
     * @param source 源ByteBuffer
     */
    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBuffers.debugAll(target);
            }
        }
        source.compact();
    }
}
