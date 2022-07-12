package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Members Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupMembersRequestMessage implements IMessage {

    private static final long serialVersionUID = 624272774132013875L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_MEMBERS_REQUEST_MESSAGE.getType();
    }
}
