package io.github.llnancy.jingtian.javase.nio.network.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO Selector模式 接收Server端写数据的Client端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/14
 */
@Slf4j
public class NIOSelectorWriteClient {

    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.connect(new InetSocketAddress("127.0.0.1", 8080));
            // 接收Server端写的数据
            int count = 0;
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                int read = sc.read(buffer);
                LOGGER.info("read: {}", read);
                count += read;
                LOGGER.info("count: {}", count);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
