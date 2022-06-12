package com.sunchaser.sparrow.javase.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import static com.sunchaser.sparrow.javase.nio.ByteBuffers.debugAll;

/**
 * Test ByteBuffer Read Write
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
@Slf4j
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);// a
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});// b, c, d
        debugAll(buffer);
        // 不切换为读模式，直接读则从是从position开始读，会读不到写入的数据。
        // System.out.println(buffer.get());
        // 切换为读模式
        buffer.flip();
        LOGGER.info("{}", buffer.get());
        debugAll(buffer);
        buffer.compact();// 切换到写入模式，并且将未读取的内容往前移。
        debugAll(buffer);
        buffer.put(new byte[]{0x65, 0x66});// e, f
        debugAll(buffer);
    }
}
