package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * prototype作用域
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/25
 */
@Component
//@Scope("prototype")
@Scope(scopeName = SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypeScopeBean {
}
