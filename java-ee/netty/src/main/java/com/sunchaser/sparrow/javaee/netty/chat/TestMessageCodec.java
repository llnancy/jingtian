package com.sunchaser.sparrow.javaee.netty.chat;

import com.sunchaser.sparrow.javaee.netty.chat.msg.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * Test MessageCodec
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class TestMessageCodec {

    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 11, 4, 0, 0),
                new LoggingHandler(),
                new MessageCodec()
        );
        LoginRequestMessage message = new LoginRequestMessage("lilu", "123", "SunChaser");
        // encode
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

        // 入站
        channel.writeInbound(buf);
    }
}
