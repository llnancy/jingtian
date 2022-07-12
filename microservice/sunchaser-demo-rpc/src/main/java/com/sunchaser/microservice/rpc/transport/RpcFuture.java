package com.sunchaser.microservice.rpc.transport;

import io.netty.util.concurrent.Promise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcFuture<V> {

    private Promise<V> promise;
}
