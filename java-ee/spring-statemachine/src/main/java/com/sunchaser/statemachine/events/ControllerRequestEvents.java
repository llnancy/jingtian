package com.sunchaser.statemachine.events;

/**
 * @author sunchaser
 * @date 2019/9/23
 * @description 控制器层请求事件
 */
public enum ControllerRequestEvents {
    /**
     * 打印入参事件
     */
    LOG_PARAMS,

    /**
     * 构建service层入参事件
     */
    GENERATE_SERVICE_PARAMS,

    /**
     * 调用service事件
     */
    CALL_SERVICE,
}
