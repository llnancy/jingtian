package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Chat Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class ChatRequestMessage implements IMessage {

    private static final long serialVersionUID = -7354622031478296886L;

    @Override
    public int type() {
        return MessageTypeEnum.CHAT_REQUEST_MESSAGE.getType();
    }
}
