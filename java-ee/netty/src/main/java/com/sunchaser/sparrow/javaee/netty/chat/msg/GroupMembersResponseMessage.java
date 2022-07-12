package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Members Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupMembersResponseMessage implements IMessage {

    private static final long serialVersionUID = 7404223294607810836L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_MEMBERS_RESPONSE_MESSAGE.getType();
    }
}
