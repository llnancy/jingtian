package com.sunchaser.microservice.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * protocol header
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header {

    /**
     * 魔数
     */
    private short magic;

    /**
     * 版本号
     */
    private byte version;

    /**
     * 协议信息
     */
    private byte protocolInfo;

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 消息体长度
     */
    private Integer size;
}
