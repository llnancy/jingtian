package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Chat Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupChatResponseMessage implements IMessage {

    private static final long serialVersionUID = 7697479175243696500L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_CHAT_RESPONSE_MESSAGE.getType();
    }
}
