package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Chat Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupChatRequestMessage implements IMessage {

    private static final long serialVersionUID = 8038684425849421515L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_CHAT_REQUEST_MESSAGE.getType();
    }
}
