package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

/**
 * request作用域
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/1
 */
@Component
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestScope
public class RequestScopeBean {
}
