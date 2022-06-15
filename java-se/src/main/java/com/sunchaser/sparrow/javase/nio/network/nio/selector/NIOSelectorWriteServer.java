package com.sunchaser.sparrow.javase.nio.network.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * NIO Selector模式 Server端向Client端写数据
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/14
 */
@Slf4j
public class NIOSelectorWriteServer {

    public static void main(String[] args) {
        try (Selector selector = Selector.open()) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            ssc.bind(new InetSocketAddress(8080));
            while (true) {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isAcceptable()) {
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        // 将SocketChannel注册到selector上。0在这里无实际意义，表示该scKey可能已经关注过其它事件。
                        SelectionKey scKey = sc.register(selector, 0, null);
                        // 模拟向客户端发送大量数据
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < 30000000; i++) {
                            sb.append("a");
                        }
                        ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                        /*
                        // 此种写法会出现buffer.hasRemaining()为true，但channel不可写情况（由操作系统缓冲区决定）
                        // 如果一直不可写，server端就会一直阻塞在此处进行while循环尝试写入。
                        while (buffer.hasRemaining()) {
                            // 返回值为实际写入的字节数
                            int write = sc.write(buffer);
                            LOGGER.info("write: {}", write);
                        }
                         **/

                        /*
                         * 正确的做法：
                         * 先写一次，然后判断buffer是否有剩余，如果有，则让sscKey关注可写事件，然后把未写完的buffer关联到sscKey上。
                         * 当可写事件就绪时，会被select()方法轮询出来进行触发，在可写事件的处理中再继续把buffer写入channel中；
                         * 当buffer写完时，清除与key的关联，并让key取消关注可写事件。
                         */
                        // 返回值为实际写入的字节数
                        int write = sc.write(buffer);
                        LOGGER.info("write: {}", write);
                        // 判断buffer是否有剩余
                        if (buffer.hasRemaining()) {
                            // 用已关注的事件加上需要关注的事件，避免覆盖。（也可使用 | 运算符）
                            scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                            // 将未写完的buffer与sscKey进行关联
                            scKey.attach(buffer);
                        }
                    } else if (key.isWritable()) {
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        SocketChannel sc = (SocketChannel) key.channel();
                        int write = sc.write(buffer);
                        LOGGER.info("write: {}", write);
                        if (!buffer.hasRemaining()) {
                            // 让key取消关注可写事件
                            key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                            // 清除buffer与key的关联
                            key.attach(null);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
