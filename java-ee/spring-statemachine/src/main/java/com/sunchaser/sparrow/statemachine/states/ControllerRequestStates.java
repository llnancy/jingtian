package com.sunchaser.sparrow.statemachine.states;

/**
 * 控制器层请求状态枚举
 * @author sunchaser
 * @since JDK8 2019/9/23
 */
public enum ControllerRequestStates {

    /**
     * 初始状态
     */
    INIT,

    /**
     * 打印完方法入参状态
     */
    LOGGED_PARAMS,

    /**
     * 未调用service层状态
     */
    BEFORE_SERVICE,

    /**
     * 校验service层返回结果
     */
    CHECK_SERVICE_RESULT,

    /**
     * 调用service成功
     */
    CALLED_SERVICE_SUCCESS,

    /**
     * 成功响应客户端状态
     */
    SUCCESS;
}
