package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

/**
 * session作用域
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/2
 */
@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@SessionScope
public class SessionScopeBean {
}
