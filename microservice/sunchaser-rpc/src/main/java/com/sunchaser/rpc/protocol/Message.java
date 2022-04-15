package com.sunchaser.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Header header;
    private Request request;
}
