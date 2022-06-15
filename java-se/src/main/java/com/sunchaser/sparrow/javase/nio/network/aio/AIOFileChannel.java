package com.sunchaser.sparrow.javase.nio.network.aio;

import com.sunchaser.sparrow.javase.nio.ByteBuffers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件AIO
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class AIOFileChannel {

    public static void main(String[] args) {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/network/aio/AIOFileChannel.java"), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            LOGGER.debug("read begin...");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                // 回调线程默认为Executors.newCachedThreadPool创建的守护线程，所以主线程不能先退出
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    LOGGER.debug("read completed...");
                    attachment.flip();
                    ByteBuffers.debugAll(attachment);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    LOGGER.debug("read failed...", exc);
                }
            });
            LOGGER.debug("read invoked...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
