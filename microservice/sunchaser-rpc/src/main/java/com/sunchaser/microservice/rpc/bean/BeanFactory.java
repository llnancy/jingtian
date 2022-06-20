package com.sunchaser.microservice.rpc.bean;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/20
 */
public class BeanFactory {

    private static final Map<String, Object> BEANS_MAP = Maps.newConcurrentMap();

    public static void register(String beanName, Object bean) {
        BEANS_MAP.put(beanName, bean);
    }

    public static Object getBean(String beanName) {
        return BEANS_MAP.get(beanName);
    }
}
