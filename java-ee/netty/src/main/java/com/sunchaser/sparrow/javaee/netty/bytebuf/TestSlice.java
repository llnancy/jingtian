package com.sunchaser.sparrow.javaee.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Test ByteBuf#slice
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
public class TestSlice {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes("abcdefghij".getBytes());
        ByteBuffs.log(buf);

        /**
         * @param index 从哪个索引开始切片
         * @param length 切多长
         */
        ByteBuf slice1 = buf.slice(0, 5);
        ByteBuf slice2 = buf.slice(5, 5);
        ByteBuffs.log(slice1);
        ByteBuffs.log(slice2);

        // slice对切片后得到的ByteBuf的最大容量进行了限制，无法增量写入。
        // slice1.writeByte('x');

        // 切片后得到的slice1仍是原来的buf（同一块内存）
        slice1.setByte(0, 'b');

        // 引用计数加一：防止误对原有buf调用release释放内存导致切片slice的ByteBuf不可用。
        // slice1.retain();
        // slice2.retain();
        // 如果直接对原有buf进行release释放内存，则会导致切片slice无法进行使用。
        // buf.release();

        ByteBuffs.log(slice1);
        ByteBuffs.log(buf);

        // 建议slice后的切片各自release。而不是再原有buf上进行release。
        slice1.release();
        slice2.release();
    }
}
