package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;

/**
 * Login Response Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class LoginResponseMessage implements IMessage {

    private static final long serialVersionUID = 7477270187962957640L;

    @Override
    public int type() {
        return MessageTypeEnum.LOGIN_RESPONSE_MESSAGE.getType();
    }
}
