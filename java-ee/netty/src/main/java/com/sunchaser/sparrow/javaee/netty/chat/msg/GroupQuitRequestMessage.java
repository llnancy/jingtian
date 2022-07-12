package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Group Quit Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class GroupQuitRequestMessage implements IMessage {

    private static final long serialVersionUID = -2872111662445490298L;

    @Override
    public int type() {
        return MessageTypeEnum.GROUP_QUIT_REQUEST_MESSAGE.getType();
    }
}
