package com.sunchaser.sparrow.javase.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * Test ByteBuffer Allocate
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
@Slf4j
public class TestByteBufferAllocate {

    public static void main(String[] args) {
        LOGGER.info("{}", ByteBuffer.allocate(16).getClass());// class java.nio.HeapByteBuffer：堆内存，读写效率较低，受到Java垃圾回收影响。
        LOGGER.info("{}", ByteBuffer.allocateDirect(16).getClass());// class java.nio.DirectByteBuffer：直接内存，读写效率高（少一次拷贝），不受垃圾回收影响。
    }
}
