package com.sunchaser.statemachine.statemachinebuilder;

import com.sunchaser.statemachine.events.ControllerRequestEvents;
import com.sunchaser.statemachine.states.ControllerRequestStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author sunchaser
 * @date 2019/9/23
 * @description
 */
@Component
public class ControllerRequestStateMachineBuilder {
    private final static String MACHINEID = "controllerRequestStateMachine";

    public StateMachine<ControllerRequestStates, ControllerRequestEvents> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<ControllerRequestStates,ControllerRequestEvents> builder = StateMachineBuilder.builder();
        System.out.println("构建控制器请求状态机");
        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINEID)
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(ControllerRequestStates.INIT)
                .choice(ControllerRequestStates.CHECK_SERVICE_RESULT)
                .states(EnumSet.allOf(ControllerRequestStates.class));

        builder.configureTransitions()
                // 初始状态 -> 打印完方法入参状态 | 打印入参事件
                .withExternal()
                .source(ControllerRequestStates.INIT)
                .target(ControllerRequestStates.LOGGED_PARAMS)
                .event(ControllerRequestEvents.LOG_PARAMS)
                .and()

                // 打印完方法入参状态 -> 未调用service层状态 | 构建service层入参事件
                .withExternal()
                .source(ControllerRequestStates.LOGGED_PARAMS)
                .target(ControllerRequestStates.BEFORE_SERVICE)
                .event(ControllerRequestEvents.GENERATE_SERVICE_PARAMS)
                .and()

                // 未调用service层状态 -> 校验service层返回结果 | 调用service事件
                .withExternal()
                .source(ControllerRequestStates.BEFORE_SERVICE)
                .target(ControllerRequestStates.CHECK_SERVICE_RESULT)
                .event(ControllerRequestEvents.CALL_SERVICE)
                .and()

                // 校验service层返回结果 -> 调用service成功 | 构建响应对象事件
                .withChoice()
                .source(ControllerRequestStates.CHECK_SERVICE_RESULT)
                .first(ControllerRequestStates.CALLED_SERVICE_SUCCESS, new Guard<ControllerRequestStates, ControllerRequestEvents>() {
                    @Override
                    public boolean evaluate(StateContext<ControllerRequestStates, ControllerRequestEvents> stateContext) {
                        System.out.println(stateContext.getException());
                        return false;
                    }
                }, new Action<ControllerRequestStates, ControllerRequestEvents>() {
                    @Override
                    public void execute(StateContext<ControllerRequestStates, ControllerRequestEvents> stateContext) {
                        System.out.println("状态改变时做业务处理...");
                    }
                }).last(ControllerRequestStates.SUCCESS);

        return builder.build();
    }
}
