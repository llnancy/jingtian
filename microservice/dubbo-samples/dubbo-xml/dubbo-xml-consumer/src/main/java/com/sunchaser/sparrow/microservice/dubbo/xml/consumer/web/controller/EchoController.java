package com.sunchaser.sparrow.microservice.dubbo.xml.consumer.web.controller;

import com.sunchaser.sparrow.microservice.dubbo.facade.EchoFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/19
 */
@RestController
public class EchoController {
    @Resource
    private EchoFacade echoFacade;

    @GetMapping("echo")
    public String echo(String msg) {
        return echoFacade.echo(msg);
    }
}
