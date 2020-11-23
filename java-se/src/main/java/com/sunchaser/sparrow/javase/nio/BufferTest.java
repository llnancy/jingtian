package com.sunchaser.sparrow.javase.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author sunchaser
 * @since JDK8 2020/1/7
 *
 */
public class BufferTest {
    @Test
    public void test() {
        String string = "abcde";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        ByteBuffer.allocateDirect(1024);
        int capacity = byteBuffer.capacity();
        int limit = byteBuffer.limit();
        int position = byteBuffer.position();
        System.out.println(capacity);
        System.out.println(limit);
        System.out.println(position);
        byteBuffer.put(string.getBytes());
    }
}
