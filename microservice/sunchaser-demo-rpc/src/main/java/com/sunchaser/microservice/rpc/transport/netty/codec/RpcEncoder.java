package com.sunchaser.microservice.rpc.transport.netty.codec;

import com.sunchaser.microservice.rpc.common.RpcContext;
import com.sunchaser.microservice.rpc.protocol.Header;
import com.sunchaser.microservice.rpc.protocol.Message;
import com.sunchaser.microservice.rpc.protocol.Request;
import com.sunchaser.microservice.rpc.serialize.compressor.Compressor;
import com.sunchaser.microservice.rpc.serialize.compressor.factory.CompressorFactory;
import com.sunchaser.microservice.rpc.serialize.serializator.Serializer;
import com.sunchaser.microservice.rpc.serialize.serializator.factory.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/13
 */
public class RpcEncoder extends MessageToByteEncoder<Message<Request>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message<Request> msg, ByteBuf out) throws Exception {
        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getProtocolInfo());
        out.writeLong(header.getMessageId());
        Request request = msg.getContent();
        Byte extraInfo = header.getProtocolInfo();
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
