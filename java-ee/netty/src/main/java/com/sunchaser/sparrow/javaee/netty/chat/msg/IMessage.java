package com.sunchaser.sparrow.javaee.netty.chat.msg;

import java.io.Serializable;

/**
 * 聊天消息顶层接口
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
public interface IMessage extends Serializable {

    default int sequenceId() {
        return 0;
    }

    int type();
}
