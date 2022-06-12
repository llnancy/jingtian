package com.sunchaser.sparrow.javase.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.sunchaser.sparrow.javase.nio.ByteBuffers.debugAll;

/**
 * Test conversion between ByteBuffer and String
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
@Slf4j
public class TestByteBufferString {

    public static void main(String[] args) {
        // 1. 字符串转为ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());// position=5，需要手动调用flip()方法切换为读模式。
        debugAll(buffer);

        // 2. Charset
        buffer = StandardCharsets.UTF_8.encode("hello");// position=0 自动切换为读模式
        debugAll(buffer);

        // 3. wrap
        buffer = ByteBuffer.wrap("hello".getBytes());// position=0 自动切换为读模式
        debugAll(buffer);

        // 仅能转化已经切换为读模式的ByteBuffer
        String str = StandardCharsets.UTF_8.decode(buffer).toString();
        LOGGER.info("str={}", str);
    }
}
