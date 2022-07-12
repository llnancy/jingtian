package com.sunchaser.sparrow.javaee.netty.chat.msg;

import com.sunchaser.sparrow.javaee.netty.chat.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Request Message
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginRequestMessage implements IMessage {

    private static final long serialVersionUID = 622379994243472540L;

    private String username;

    private String password;

    private String nickname;

    @Override
    public int type() {
        return MessageTypeEnum.LOGIN_REQUEST_MESSAGE.getType();
    }
}
