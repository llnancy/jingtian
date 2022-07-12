package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Create Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupCreateResponseMessage implements IMessage {

    private static final long serialVersionUID = -4594739532087665765L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_CREATE_RESPONSE_MESSAGE.getType();
    }
}
