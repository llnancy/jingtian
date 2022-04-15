package com.sunchaser.sparrow.statemachine.states;

/**
 * 状态枚举
 * @author sunchaser
 * @since JDK8 2019/9/20
 */
public enum OrderStates {
    /**
     * 待支付状态
     */
    UNPAY,

    /**
     * 待收货状态
     */
    WAITING_FOR_RECEIVE,

    /**
     * 订单成功状态
     */
    SUCCESS;
}
