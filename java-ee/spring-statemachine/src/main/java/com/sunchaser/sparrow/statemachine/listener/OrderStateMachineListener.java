package com.sunchaser.sparrow.statemachine.listener;

import com.sunchaser.sparrow.statemachine.events.OrderEvents;
import com.sunchaser.sparrow.statemachine.states.OrderStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

/**
 * @author sunchaser
 * @date 2019/9/20
 * @description 状态机处理监听器
 */
@Component
public class OrderStateMachineListener extends StateMachineListenerAdapter<OrderStates, OrderEvents> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStateMachineListener.class);

    @Override
    public void transition(Transition<OrderStates, OrderEvents> transition) {
        if(transition.getTarget().getId() == OrderStates.UNPAY) {
            LOGGER.info("订单创建，待支付");
            return;
        }
        if(transition.getSource().getId() == OrderStates.UNPAY
                && transition.getTarget().getId() == OrderStates.WAITING_FOR_RECEIVE) {
            LOGGER.info("用户完成支付，待收货");
            return;
        }

        if(transition.getSource().getId() == OrderStates.WAITING_FOR_RECEIVE
                && transition.getTarget().getId() == OrderStates.SUCCESS) {
            LOGGER.info("用户已收货，订单完成");
            return;
        }
    }
}
