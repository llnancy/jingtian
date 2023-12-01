package io.github.llnancy.jingtian.javase.nio.bytebuffer;

import io.github.llnancy.jingtian.javase.nio.ByteBuffers;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * Test ByteBuffer Read Write
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
@Slf4j
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ByteBuffers.debugAll(buffer);

        // 写入a b c d四个字节
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        ByteBuffers.debugAll(buffer);

        // 不切换为读模式，直接读则从是从position开始读，会读不到写入的数据。
        // System.out.println(buffer.get());

        // 切换为读模式
        buffer.flip();
        ByteBuffers.debugAll(buffer);

        // 一次性读4个字节，position=4
        buffer.get(new byte[4]);
        ByteBuffers.debugAll(buffer);

        // 读指定索引位置的字符，不会改变position读索引的位置。
        LOGGER.info("{}", (char) buffer.get(1));// 读到b，position还是等于4
        ByteBuffers.debugAll(buffer);

        // rewind: 从头开始读
        buffer.rewind();
        ByteBuffers.debugAll(buffer);
        LOGGER.info("{}", (char) buffer.get());

        // clear：切换为写模式
        buffer.clear();
        buffer.put(new byte[]{'e', 'f', 'g'});
        ByteBuffers.debugAll(buffer);

        // mark & reset
        // mark是一个标记，记录position的位置；reset是将position重置到mark标记的位置
        buffer.flip();
        LOGGER.info("{}", (char) buffer.get());// e
        buffer.mark();// mark标记，position=1
        ByteBuffers.debugAll(buffer);
        LOGGER.info("{}", (char) buffer.get());// f
        LOGGER.info("{}", (char) buffer.get());// g
        ByteBuffers.debugAll(buffer);// 读了3个字节，position=3
        buffer.reset();// 将position重置为1
        ByteBuffers.debugAll(buffer);
        LOGGER.info("{}", (char) buffer.get());// 再次读取到f

        // 将未读完的字节向前压缩，然后切换为写模式。
        buffer.compact(); // g字符未读，position=1
        ByteBuffers.debugAll(buffer);
        buffer.put(new byte[]{'h', 'i', 'j', 'k'});
        ByteBuffers.debugAll(buffer);
    }
}
