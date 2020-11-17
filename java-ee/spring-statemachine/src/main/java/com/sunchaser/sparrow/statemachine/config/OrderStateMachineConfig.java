package com.sunchaser.sparrow.statemachine.config;

import com.sunchaser.sparrow.statemachine.listener.OrderStateMachineListener;
import com.sunchaser.sparrow.statemachine.events.OrderEvents;
import com.sunchaser.sparrow.statemachine.states.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author sunchaser
 * @date 2019/9/20
 * @description 单个订单状态机配置类
 */
@EnableStateMachine
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Autowired
    private OrderStateMachineListener orderStateMachineListener;

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates()
                .initial(OrderStates.UNPAY)
                .states(EnumSet.allOf(OrderStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderStates.UNPAY).target(OrderStates.WAITING_FOR_RECEIVE) // 指定状态来源和目标
                .event(OrderEvents.PAY)    // 指定触发事件
                .and()
                .withExternal()
                .source(OrderStates.WAITING_FOR_RECEIVE).target(OrderStates.SUCCESS)
                .event(OrderEvents.RECEIVE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config.withConfiguration()
                .listener(orderStateMachineListener);  // 指定状态机的处理监听器
    }
}
