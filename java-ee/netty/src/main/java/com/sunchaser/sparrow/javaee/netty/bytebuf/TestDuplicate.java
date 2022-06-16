package com.sunchaser.sparrow.javaee.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Test Duplicate
 * <p>
 * 体现了零拷贝，截取原始ByteBuf所有内容，并且没有最大容量maxCapacity的限制，与原始ByteBuf是同一块内存，读写指针是独立的。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
public class TestDuplicate {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes("abcdefghij".getBytes());
        ByteBuffs.log(buf);

        ByteBuf duplicate = buf.duplicate();
        ByteBuffs.log(duplicate);

        duplicate.setByte(0, 'x');
        ByteBuffs.log(buf);
    }
}
