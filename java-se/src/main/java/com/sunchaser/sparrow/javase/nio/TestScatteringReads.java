package com.sunchaser.sparrow.javase.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

import static com.sunchaser.sparrow.javase.nio.ByteBuffers.debugAll;

/**
 * Scattering Reads 分散读
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
public class TestScatteringReads {

    public static void main(String[] args) {
        // 长度已知，一次读取到多个ByteBuffer中
        try (FileChannel channel = new RandomAccessFile(Utils.path("reads.txt"), "r").getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[] {b1, b2, b3});
            b1.flip();
            b2.flip();
            b3.flip();
            debugAll(b1);
            debugAll(b2);
            debugAll(b3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
