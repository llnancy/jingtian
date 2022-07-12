package com.sunchaser.microservice.rpc.transport.netty.codec;

import com.sunchaser.microservice.rpc.common.RpcContext;
import com.sunchaser.microservice.rpc.protocol.Header;
import com.sunchaser.microservice.rpc.protocol.Message;
import com.sunchaser.microservice.rpc.protocol.Request;
import com.sunchaser.microservice.rpc.serialize.compressor.factory.CompressorFactory;
import com.sunchaser.microservice.rpc.serialize.serializator.Serializer;
import com.sunchaser.microservice.rpc.serialize.compressor.Compressor;
import com.sunchaser.microservice.rpc.serialize.serializator.factory.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < RpcContext.HEADER_SIZE) {// 不足消息头长度16字节，暂不读取
            return;
        }
        // 记录当前读指针readerIndex的位置
        byteBuf.markReaderIndex();
        // 读取魔数
        short magic = byteBuf.readShort();
        if (magic != RpcContext.MAGIC) {
            byteBuf.resetReaderIndex();
            throw new RuntimeException("magic number error: " + magic);
        }
        // 读取版本号
        byte version = byteBuf.readByte();
        // 读取附加信息
        byte extraInfo = byteBuf.readByte();
        // 读取消息ID
        long messageId = byteBuf.readLong();
        // 读取消息体长度
        int size = byteBuf.readInt();
        Request request = null;
        if (!RpcContext.isHeartBeat(extraInfo)) {// 非心跳消息
            // 不足size无法解析
            if (byteBuf.readableBytes() < size) {
                byteBuf.resetReaderIndex();
                return;
            }
            byte[] payload = new byte[size];
            byteBuf.readBytes(payload);
            Serializer serializer = SerializerFactory.get(extraInfo);
            Compressor compressor = CompressorFactory.get(extraInfo);
            request = serializer.deserialize(compressor.unCompress(payload), Request.class);
        }
        Header header = new Header(magic, version, extraInfo, messageId, size);
        Message message = new Message(header, request);
        list.add(message);
    }
}
