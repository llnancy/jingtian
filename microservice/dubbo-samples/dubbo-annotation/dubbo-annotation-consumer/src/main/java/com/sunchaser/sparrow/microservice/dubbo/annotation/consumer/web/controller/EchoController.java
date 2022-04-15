package com.sunchaser.sparrow.microservice.dubbo.annotation.consumer.web.controller;

import com.sunchaser.sparrow.microservice.dubbo.facade.EchoFacade;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/26
 */
@RestController
public class EchoController {

    @Reference(check = false)
    private EchoFacade echoFacade;

    @GetMapping("echo")
    public String echo(String msg) {
        return echoFacade.echo(msg);
    }
}
