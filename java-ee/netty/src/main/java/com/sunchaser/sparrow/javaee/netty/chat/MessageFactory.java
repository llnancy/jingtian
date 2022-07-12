package com.sunchaser.sparrow.javaee.netty.chat;

/**
 * 消息类型工厂
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public class MessageFactory {

    public static final int LOGIN_REQUEST_MESSAGE = 0;
    public static final int LOGIN_RESPONSE_MESSAGE = 1;
    public static final int CHAT_REQUEST_MESSAGE = 2;
    public static final int CHAT_RESPONSE_MESSAGE = 3;
    public static final int GROUP_CREATE_REQUEST_MESSAGE = 4;
    public static final int GROUP_CREATE_RESPONSE_MESSAGE = 5;
    public static final int GROUP_JOIN_REQUEST_MESSAGE = 6;
    public static final int GROUP_JOIN_RESPONSE_MESSAGE = 7;
    public static final int GROUP_QUIT_REQUEST_MESSAGE = 8;
    public static final int GROUP_QUIT_RESPONSE_MESSAGE = 9;
    public static final int GROUP_CHAT_REQUEST_MESSAGE = 10;
    public static final int GROUP_CHAT_RESPONSE_MESSAGE = 11;
    public static final int GROUP_MEMBERS_REQUEST_MESSAGE = 12;
    public static final int GROUP_MEMBERS_RESPONSE_MESSAGE = 13;
}
