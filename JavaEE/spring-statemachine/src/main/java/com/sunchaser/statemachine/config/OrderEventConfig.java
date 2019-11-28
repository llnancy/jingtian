package com.sunchaser.statemachine.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author sunchaser
 * @date 2019/9/20
 * @description 注解配置订单状态机触发事件，等同于状态机处理监听器
 */
@WithStateMachine(id = "orderStateMachine")
public class OrderEventConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventConfig.class);

    @OnTransition(target = "UNPAY")
    public void create() {
        LOGGER.info("订单创建，待支付...");
    }

    @OnTransition(source = "UNPAY", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        LOGGER.info("用户完成支付，待收货...");
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "SUCCESS")
    public void receive() {
        LOGGER.info("用户已收货，订单完成...");
    }
}
