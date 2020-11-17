package com.sunchaser.sparrow.statemachine.states;

/**
 * @author sunchaser
 * @date 2019/9/20
 * @description 状态枚举
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
