package cn.org.lilu.states;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/20
 * @Description: 状态枚举
 */
public enum States {
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
