package com.sunchaser.sparrow.javaee.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * Netty ByteBuf 工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class ByteBuffs {

    public static void log(ByteBuf buf) {
        int length = buf.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder append = new StringBuilder(rows * 80 * 2)
                .append("read index:")
                .append(buf.readerIndex())
                .append("; write index:")
                .append(buf.writerIndex())
                .append("; capacity:")
                .append(buf.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(append, buf);
        LOGGER.info(append.toString());
    }
}
