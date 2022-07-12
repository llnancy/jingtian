package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Quit Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupQuitResponseMessage implements IMessage {

    private static final long serialVersionUID = -4709903464837929381L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_QUIT_RESPONSE_MESSAGE.getType();
    }
}
