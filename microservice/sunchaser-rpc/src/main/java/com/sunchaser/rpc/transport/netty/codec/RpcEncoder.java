package com.sunchaser.rpc.transport.netty.codec;

import com.sunchaser.rpc.common.RpcContext;
import com.sunchaser.rpc.protocol.Header;
import com.sunchaser.rpc.protocol.Message;
import com.sunchaser.rpc.protocol.Request;
import com.sunchaser.rpc.serialize.compressor.Compressor;
import com.sunchaser.rpc.serialize.compressor.factory.CompressorFactory;
import com.sunchaser.rpc.serialize.serializator.Serializer;
import com.sunchaser.rpc.serialize.serializator.factory.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/13
 */
public class RpcEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getExtraInfo());
        out.writeLong(header.getMessageId());
        Request request = msg.getRequest();
        Byte extraInfo = header.getExtraInfo();
        if (RpcContext.isHeartBeat(extraInfo)) {
            // 心跳消息，无消息体
            out.writeInt(0);
            return;
        }
        Serializer serializer = SerializerFactory.get(extraInfo);
        Compressor compressor = CompressorFactory.get(extraInfo);
        byte[] payload = compressor.compress(serializer.serialize(request));
        out.writeInt(payload.length);
        out.writeBytes(payload);
    }
}
