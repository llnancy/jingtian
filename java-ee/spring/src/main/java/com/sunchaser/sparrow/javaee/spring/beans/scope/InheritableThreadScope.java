package com.sunchaser.sparrow.javaee.spring.beans.scope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 父子线程范围内的作用域
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/6
 */
@Slf4j
@Component
public class InheritableThreadScope implements Scope, BeanFactoryPostProcessor {

    /**
     * 作用域存储：
     * Map<String, Object>
     * key：bean name
     * value：bean instance
     */
    private final ThreadLocal<Map<String, Object>> inheritableThreadScope = new NamedInheritableThreadLocal<Map<String, Object>>("InheritableThreadScope") {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        // 获取InheritableThreadLocal当中的Map
        Map<String, Object> scope = this.inheritableThreadScope.get();
        Object scopeObject = scope.get(name);
        if (scopeObject == null) {
            scopeObject = objectFactory.getObject();
            scope.put(name, scopeObject);
        }
        return scopeObject;
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> scope = this.inheritableThreadScope.get();
        return scope.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        log.warn("InheritableThreadScope does not support destruction callbacks. " +
                "Consider using RequestScope in a web environment.");
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerScope("inheritableThreadScope", this);
    }
}
