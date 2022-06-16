package com.sunchaser.sparrow.javaee.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

/**
 * Test CompositeByteBuf
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
public class TestCompositeByteBuf {

    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes("12345".getBytes());

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes("678910".getBytes());

        // 将两个小的ByteBuf合并为一个ByteBuf

        // 方式一：创建一个新的ByteBuf依次写入两个小的ByteBuf。进行了数据的内存复制操作，效率不高。
        // ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        // buf.writeBytes(buf1).writeBytes(buf2);
        // ByteBuffs.log(buf);

        // 方式二：CompositeByteBuf。内部用一个数组记录每一个小的ByteBuf相对于整体的偏移量，无内存复制操作。
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        // 第一个参数传递true，表示自动增长写指针的位置，保证ByteBuf的正常写入。
        compositeByteBuf.addComponents(true, buf1, buf2);
        ByteBuffs.log(compositeByteBuf);
    }
}
