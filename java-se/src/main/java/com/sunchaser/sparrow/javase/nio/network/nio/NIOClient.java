package com.sunchaser.sparrow.javase.nio.network.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * NIO Client端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/13
 */
@Slf4j
public class NIOClient {

    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.connect(new InetSocketAddress("127.0.0.1", 8080));
            LOGGER.debug("waiting");
            sc.write(Charset.defaultCharset().encode("hello\n"));
            sc.write(Charset.defaultCharset().encode("0123456789abcdefgsunchaser\n"));
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
