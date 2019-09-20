package cn.org.lilu.config;

import cn.org.lilu.listener.OrderStateMachineListener;
import cn.org.lilu.states.Events;
import cn.org.lilu.states.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.transition.Transition;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/20
 * @Description:
 */
@WithStateMachine
public class EventConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConfig.class);

    @OnTransition(target = "UNPAY")
    public void create() {
        LOGGER.info("订单创建，待支付");
    }

    @OnTransition(source = "UNPAY", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        LOGGER.info("用户完成支付，待收货");
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "SUCCESS")
    public void receive() {
        LOGGER.info("用户已收货，订单完成");
    }
}
