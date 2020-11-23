package com.sunchaser.sparrow.statemachine.controller;

import com.sunchaser.sparrow.statemachine.events.ControllerRequestEvents;
import com.sunchaser.sparrow.statemachine.statemachinebuilder.OrderStateMachineBuilder;
import com.sunchaser.sparrow.statemachine.statemachinebuilder.ControllerRequestStateMachineBuilder;
import com.sunchaser.sparrow.statemachine.events.OrderEvents;
import com.sunchaser.sparrow.statemachine.states.ControllerRequestStates;
import com.sunchaser.sparrow.statemachine.states.OrderStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser
 * @since JDK8 2019/9/23
 */
@RestController
@RequestMapping("/statemachine")
public class StateMachineController {

    @Autowired
    private StateMachine<OrderStates, OrderEvents> orderStateMachine;

    @Autowired
    private OrderStateMachineBuilder orderStateMachineBuilder;

    @Autowired
    private ControllerRequestStateMachineBuilder controllerRequestStateMachineBuilder;

    @Autowired
    private BeanFactory beanFactory;

    /**
     * 单一订单状态机
     */
    @RequestMapping("/testSingleOrderState")
    public void testSingleOrderState() {
        orderStateMachine.start();
        orderStateMachine.sendEvent(OrderEvents.PAY);
        orderStateMachine.sendEvent(OrderEvents.RECEIVE);
        System.out.println("最终状态：" + orderStateMachine.getState().getId());
    }

    /**
     * 多个订单状态机共存
     */
    @RequestMapping("/testMultiOrderState")
    public void testMultiOrderState() throws Exception {
        StateMachine<OrderStates, OrderEvents> orderStateMachine = orderStateMachineBuilder.build(beanFactory);
        System.out.println(orderStateMachine.getId());
        orderStateMachine.start();
        orderStateMachine.sendEvent(OrderEvents.PAY);
        orderStateMachine.sendEvent(OrderEvents.RECEIVE);
        System.out.println("最终状态：" + orderStateMachine.getState().getId());
    }

    @RequestMapping("/testControllerRequestState")
    public void testControllerRequestState() throws Exception {
        StateMachine<ControllerRequestStates, ControllerRequestEvents> controllerRequestEventsStateMachine = controllerRequestStateMachineBuilder.build(beanFactory);
        System.out.println(controllerRequestEventsStateMachine.getId());
        controllerRequestEventsStateMachine.start();
        controllerRequestEventsStateMachine.sendEvent(ControllerRequestEvents.LOG_PARAMS);
        controllerRequestEventsStateMachine.sendEvent(ControllerRequestEvents.GENERATE_SERVICE_PARAMS);
        controllerRequestEventsStateMachine.sendEvent(ControllerRequestEvents.CALL_SERVICE);
        System.out.println("最终状态：" + controllerRequestEventsStateMachine.getState().getId());
    }
}
