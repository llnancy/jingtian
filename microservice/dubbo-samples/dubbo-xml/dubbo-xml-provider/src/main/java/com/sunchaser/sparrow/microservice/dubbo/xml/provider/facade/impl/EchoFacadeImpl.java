package com.sunchaser.sparrow.microservice.dubbo.xml.provider.facade.impl;

import com.sunchaser.sparrow.microservice.dubbo.facade.EchoFacade;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/17
 */
@Service(value = "echoFacade")
public class EchoFacadeImpl implements EchoFacade {
    @Override
    public String echo(String msg) {
        return "[" + LocalDateTime.now() + "] echo:" + msg + ", request from consumer:" + RpcContext.getContext().getRemoteAddress();
    }
}
