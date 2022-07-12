package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Chat Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class ChatResponseMessage implements IMessage {

    private static final long serialVersionUID = -3870618658240080531L;

    @Override
    public int type() {
        return MessageTypeEnum.CHAT_REQUEST_MESSAGE.getType();
    }
}
