package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Join Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupJoinResponseMessage implements IMessage {

    private static final long serialVersionUID = -6904090910407356299L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_JOIN_RESPONSE_MESSAGE.getType();
    }
}
