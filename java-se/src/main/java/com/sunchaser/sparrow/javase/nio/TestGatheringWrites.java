package com.sunchaser.sparrow.javase.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * Gathering Writes 集中写
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
public class TestGatheringWrites {

    public static void main(String[] args) {
        // 多个ByteBuffer同时写入到一个文件中
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");

        try (FileChannel channel = new RandomAccessFile(Utils.path("writes.txt"), "rw").getChannel()) {
            channel.write(new ByteBuffer[] {b1, b2, b3});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
