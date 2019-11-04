package com.sunchaser.statemachinebuilder;

import com.sunchaser.events.OrderEvents;
import com.sunchaser.states.OrderStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author: sunchaser
 * @date: 2019/9/23
 * @description: 订单状态机构建器
 */
@Component
public class OrderStateMachineBuilder {
    private final static String MACHINEID = "orderStateMachine";

    public StateMachine<OrderStates, OrderEvents> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<OrderStates,OrderEvents> builder = StateMachineBuilder.builder();
        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINEID)
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(OrderStates.UNPAY)
                .states(EnumSet.allOf(OrderStates.class));

        builder.configureTransitions()
                .withExternal()
                .source(OrderStates.UNPAY).target(OrderStates.WAITING_FOR_RECEIVE)
                .event(OrderEvents.PAY).action(action())
                .and()
                .withExternal()
                .source(OrderStates.WAITING_FOR_RECEIVE).target(OrderStates.SUCCESS)
                .event(OrderEvents.RECEIVE);
        return builder.build();
    }

    @Bean
    public Action<OrderStates,OrderEvents> action() {
        return System.out::println;
    }
}
