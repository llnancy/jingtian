package com.sunchaser.sparrow.javase.nio.bytebuffer;

import com.sunchaser.sparrow.javase.nio.Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ByteBuffer
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/25
 */
@Slf4j
public class ByteBufferTest {

    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream(Utils.path(ByteBufferTest.class, "data.txt")).getChannel()) {
            // 给buffer分配10个字节
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // 从channel读取数据到buffer
            while (channel.read(buffer) != -1) {
                // 切换至读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    LOGGER.info("读取到的字节：{}", (char) b);
                }
                // 切换至写模式
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
