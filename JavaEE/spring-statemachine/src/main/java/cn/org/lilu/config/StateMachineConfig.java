package cn.org.lilu.config;

import cn.org.lilu.listener.OrderStateMachineListener;
import cn.org.lilu.states.Events;
import cn.org.lilu.states.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/20
 * @Description: 状态机配置类
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineConfig.class);

    @Autowired
    private OrderStateMachineListener orderStateMachineListener;

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.UNPAY)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.UNPAY).target(States.WAITING_FOR_RECEIVE) // 指定状态来源和目标
                .event(Events.PAY)    // 指定触发事件
                .and()
                .withExternal()
                .source(States.WAITING_FOR_RECEIVE).target(States.SUCCESS)
                .event(Events.RECEIVE);
    }

//    @Override
//    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
//        config.withConfiguration()
//                .listener(orderStateMachineListener);  // 指定状态机的处理监听器
//    }
}
