package com.sunchaser.sparrow.javaee.netty.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {

    LOGIN_REQUEST_MESSAGE(0),

    LOGIN_RESPONSE_MESSAGE(1),

    CHAT_REQUEST_MESSAGE(2),

    CHAT_RESPONSE_MESSAGE(3),

    GROUP_CREATE_REQUEST_MESSAGE(4),

    GROUP_CREATE_RESPONSE_MESSAGE(5),

    GROUP_JOIN_REQUEST_MESSAGE(6),

    GROUP_JOIN_RESPONSE_MESSAGE(7),

    GROUP_QUIT_REQUEST_MESSAGE(8),

    GROUP_QUIT_RESPONSE_MESSAGE(9),

    GROUP_CHAT_REQUEST_MESSAGE(10),

    GROUP_CHAT_RESPONSE_MESSAGE(11),

    GROUP_MEMBERS_REQUEST_MESSAGE(12),

    GROUP_MEMBERS_RESPONSE_MESSAGE(13)
    ;

    private final int type;
}
