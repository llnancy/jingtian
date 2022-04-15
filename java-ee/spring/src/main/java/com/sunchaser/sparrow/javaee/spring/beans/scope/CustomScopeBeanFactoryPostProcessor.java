package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Component;

/**
 * BeanFactoryPostProcessor扩展点注册自定义作用域
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/6
 */
@Component
public class CustomScopeBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerScope("simpleThreadScope", new SimpleThreadScope());
    }
}
