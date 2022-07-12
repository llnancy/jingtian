package com.sunchaser.microservice.rpc.transport.netty.codec;

import com.sunchaser.microservice.rpc.common.RpcContext;
import com.sunchaser.microservice.rpc.protocol.Header;
import com.sunchaser.microservice.rpc.protocol.Message;
import com.sunchaser.microservice.rpc.protocol.Request;
import com.sunchaser.microservice.rpc.protocol.Response;
import com.sunchaser.microservice.rpc.serialize.compressor.Compressor;
import com.sunchaser.microservice.rpc.serialize.compressor.factory.CompressorFactory;
import com.sunchaser.microservice.rpc.serialize.serializator.Serializer;
import com.sunchaser.microservice.rpc.serialize.serializator.factory.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/18
 */
public class RpcCodec extends ByteToMessageCodec<Message<Request>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message<Request> msg, ByteBuf out) throws Exception {
        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getProtocolInfo());
        out.writeLong(header.getMessageId());
        Object content = msg.getContent();
        byte protocolInfo = header.getProtocolInfo();
        if (RpcContext.isHeartBeat(protocolInfo)) {
            // 心跳消息，无消息体
            out.writeInt(0);
            return;
        }
        Serializer serializer = SerializerFactory.get(protocolInfo);
        Compressor compressor = CompressorFactory.get(protocolInfo);
        byte[] payload = compressor.compress(serializer.serialize(content));
        out.writeInt(payload.length);
        out.writeBytes(payload);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < RpcContext.HEADER_SIZE) {// 不足消息头长度16字节，暂不读取
            return;
        }
        // 记录当前读指针readerIndex的位置
        in.markReaderIndex();
        // 读取魔数
        short magic = in.readShort();
        if (magic != RpcContext.MAGIC) {
            in.resetReaderIndex();
            throw new RuntimeException("magic number error: " + magic);
        }
        // 读取版本号
        byte version = in.readByte();
        // 读取附加信息
        byte extraInfo = in.readByte();
        // 读取消息ID
        long messageId = in.readLong();
        // 读取消息体长度
        int size = in.readInt();
        Object body = null;
        if (!RpcContext.isHeartBeat(extraInfo)) {// 非心跳消息
            // 不足size无法解析
            if (in.readableBytes() < size) {
                in.resetReaderIndex();
                return;
            }
            byte[] payload = new byte[size];
            in.readBytes(payload);
            Serializer serializer = SerializerFactory.get(extraInfo);
            Compressor compressor = CompressorFactory.get(extraInfo);
            if (RpcContext.isRpc(extraInfo)) {
                body = serializer.deserialize(compressor.unCompress(payload), Request.class);
            } else {
                body = serializer.deserialize(compressor.unCompress(payload), Response.class);
            }
        }
        Header header = new Header(magic, version, extraInfo, messageId, size);
        Message<Object> message = new Message<>(header, body);
        out.add(message);
    }
}
