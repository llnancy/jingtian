package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Join Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupJoinRequestMessage implements IMessage {

    private static final long serialVersionUID = 6267366746932865579L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_JOIN_REQUEST_MESSAGE.getType();
    }
}
