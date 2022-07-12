package com.sunchaser.microservice.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC message
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message<T> {

    private Header header;

    private T content;
}
