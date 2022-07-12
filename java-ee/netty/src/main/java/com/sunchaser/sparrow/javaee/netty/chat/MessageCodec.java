package com.sunchaser.sparrow.javaee.netty.chat;

import com.sunchaser.sparrow.javaee.netty.chat.msg.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * MessageCodec
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<IMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IMessage msg, ByteBuf out) throws Exception {
        // 魔数
        out.writeBytes("1234".getBytes(CharsetUtil.UTF_8));
        // 版本号
        out.writeByte(1);
        // 字节的序列化方式
        out.writeByte(0);
        // 消息类型
        out.writeByte(msg.type());
        // 请求序列号
        out.writeInt(msg.sequenceId());
        // 对象转字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] msgBytes = bos.toByteArray();
        // 发送消息的长度
        out.writeInt(msgBytes.length);
        // 发送的消息
        out.writeBytes(msgBytes);
        // 最终的字节数建议满足2的n次方倍，建议进行对齐填充。
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // CharSequence magic = in.readCharSequence(9, CharsetUtil.UTF_8);
        int magic = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        int messageLength = in.readInt();
        byte[] bytes = new byte[messageLength];
        in.readBytes(bytes);
        if (serializerType == 0) {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            IMessage message = (IMessage) ois.readObject();
            LOGGER.info("{}, {}, {}, {}, {}, {}", magic, version, serializerType, messageType, sequenceId, messageLength);
            LOGGER.info("message: {}", message);
            out.add(message);
        }
    }
}
