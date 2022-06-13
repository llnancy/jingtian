package com.sunchaser.sparrow.javase.nio.network.nio.nonblock;

import com.sunchaser.sparrow.javase.nio.ByteBuffers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * NIO非阻塞模式 Server端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/13
 */
@Slf4j
public class NIONonBlockServer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 创建服务端Channel
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            // 设置ServerSocketChannel为非阻塞模式
            ssc.configureBlocking(false);
            // 给Channel绑定监听端口
            ssc.bind(new InetSocketAddress(8080));
            // 存放连接的集合
            List<SocketChannel> channels = new ArrayList<>();
            while (true) {
                // LOGGER.debug("before accept...");
                SocketChannel sc = ssc.accept();// 非阻塞模式下：没有客户端进行连接时，返回null。
                if (sc != null) {
                    LOGGER.debug("after accept...{}", sc);
                    // 设置SocketChannel为非阻塞模式
                    sc.configureBlocking(false);
                    channels.add(sc);
                }
                for (SocketChannel channel : channels) {
                    // 读取客户端发送的数据
                    // LOGGER.debug("before read...{}", channel);
                    int read = channel.read(buffer);// 非阻塞模式下：channel中无数据可读时，返回0。
                    if (read > 0) {
                        buffer.flip();
                        ByteBuffers.debugRead(buffer);
                        buffer.clear();
                        LOGGER.debug("after read...{}", channel);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
