package com.sunchaser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author: sunchaser
 * @date: 2019/9/23
 * @description:
 */
@WithStateMachine(id = "controllerRequestStateMachine")
public class ControllerRequestEventConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerRequestEventConfig.class);

    @OnTransition(target = "INIT")
    public void init() {
        LOGGER.info("控制器接收请求...");
    }

    @OnTransition(source = "INIT",target = "LOGGED_PARAMS")
    public void printRequestParam() {
        LOGGER.info("控制器层打印请求入参...");
    }

    @OnTransition(source = "LOGGED_PARAMS",target = "BEFORE_SERVICE")
    public void generateServiceInParameter() {
        LOGGER.info("控制器层构建service层入参...");
    }

    @OnTransition(source = "BEFORE_SERVICE",target = "CHECK_SERVICE_RESULT")
    public void callServiceMethod() {
        LOGGER.info("控制器层调用service层方法，返回结果后判断调用是否成功...");
    }

    @OnTransition(source = "CHECK_SERVICE_RESULT",target = "CALLED_SERVICE_SUCCESS")
    public void callServiceMethodSuccess() {
        LOGGER.info("控制器层调用service层方法成功...");
    }

    @OnTransition(source = "CALLED_SERVICE_SUCCESS",target = "SUCCESS")
    public void generateResponse() {
        LOGGER.info("构建HTTP统一响应对象，返回前端...");
    }
}
