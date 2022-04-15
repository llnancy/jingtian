package com.sunchaser.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Header {

    /**
     * 魔数
     */
    private Short magic;

    /**
     * 版本号
     */
    private Byte version;

    /**
     * 附加信息
     */
    private Byte extraInfo;

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 消息体长度
     */
    private Integer size;
}
