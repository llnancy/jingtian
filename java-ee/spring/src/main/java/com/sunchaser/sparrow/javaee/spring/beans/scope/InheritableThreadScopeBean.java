package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * inheritableThreadScope作用域
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/6
 */
@Component
@Scope(value = "inheritableThreadScope", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InheritableThreadScopeBean {
}
