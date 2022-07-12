package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Create Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupCreateRequestMessage implements IMessage {

    private static final long serialVersionUID = -1802698831279304989L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_CREATE_REQUEST_MESSAGE.getType();
    }
}
