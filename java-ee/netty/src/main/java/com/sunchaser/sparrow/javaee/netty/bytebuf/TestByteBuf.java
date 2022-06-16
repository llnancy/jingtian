package com.sunchaser.sparrow.javaee.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * Test ByteBuf
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        // 默认pooled池化实现，可配置为非池化实现：-Dio.netty.allocator.type=unpooled
        LOGGER.info("ByteBuf class: {}", buf.getClass());// io.netty.buffer.PooledUnsafeDirectByteBuf
        // LOGGER.info("buf={}", buf);
        ByteBuffs.log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        // LOGGER.info("buf={}", buf);
        ByteBuffs.log(buf);
    }
}
