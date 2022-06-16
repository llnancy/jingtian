package com.sunchaser.sparrow.javaee.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Test Copy
 * <p>
 * 对原始数据进行深拷贝，copy出一份全新的ByteBuf，与原始ByteBuf完全无关，是两块不同的内存。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
public class TestCopy {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes("abcdefghij".getBytes());
        ByteBuffs.log(buf);

        ByteBuf copy = buf.copy();
        ByteBuffs.log(copy);

        copy.setByte(0, 'x');
        ByteBuffs.log(buf);
    }
}
