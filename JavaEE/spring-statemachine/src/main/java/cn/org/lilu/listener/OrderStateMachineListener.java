package cn.org.lilu.listener;

import cn.org.lilu.config.StateMachineConfig;
import cn.org.lilu.states.Events;
import cn.org.lilu.states.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/20
 * @Description: 状态监听器
 */
@Component
public class OrderStateMachineListener extends StateMachineListenerAdapter<States, Events> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStateMachineListener.class);

    @Override
    public void transition(Transition<States, Events> transition) {
        if(transition.getTarget().getId() == States.UNPAY) {
            LOGGER.info("订单创建，待支付");
            return;
        }
        if(transition.getSource().getId() == States.UNPAY
                && transition.getTarget().getId() == States.WAITING_FOR_RECEIVE) {
            LOGGER.info("用户完成支付，待收货");
            return;
        }

        if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE
                && transition.getTarget().getId() == States.SUCCESS) {
            LOGGER.info("用户已收货，订单完成");
            return;
        }
    }
}
