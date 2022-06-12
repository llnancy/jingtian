package com.sunchaser.sparrow.javase.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import static com.sunchaser.sparrow.javase.nio.ByteBuffers.debugAll;

/**
 * Test Buffer Read
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
@Slf4j
public class TestBufferRead {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        // 切换为读模式
        buffer.flip();

        // 一次性读4个字节，position=4
        // buffer.get(new byte[4]);
        // debugAll(buffer);
        // // rewind: 从头开始读
        // buffer.rewind();
        // LOGGER.info("{}", (char) buffer.get());

        // mark & reset
        // mark是一个标记，记录position的位置；reset是将position重置到mark的位置
        // LOGGER.info("{}", (char) buffer.get());
        // LOGGER.info("{}", (char) buffer.get());
        // buffer.mark();// mark标记，position=2
        // LOGGER.info("{}", (char) buffer.get());
        // LOGGER.info("{}", (char) buffer.get());
        // buffer.reset();// 将position重置为2
        // LOGGER.info("{}", (char) buffer.get());
        // LOGGER.info("{}", (char) buffer.get());

        // 读指定索引位置的字符，不会改变position读索引的位置。
        LOGGER.info("{}", (char) buffer.get(3));
        debugAll(buffer);
    }
}
