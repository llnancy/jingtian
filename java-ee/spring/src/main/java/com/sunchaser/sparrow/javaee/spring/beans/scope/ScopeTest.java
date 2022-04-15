package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * scopes test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/25
 */
@RestController
public class ScopeTest implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private SingletonScopeBean singletonScopeBean;

    @GetMapping("/singleton")
    public void singleton() {
        System.out.println("singleton @Autowired第一次：" + singletonScopeBean);
        System.out.println("singleton @Autowired第二次：" + singletonScopeBean);
        System.out.println("singleton getBean第一次：" + applicationContext.getBean(SingletonScopeBean.class));
        System.out.println("singleton getBean第二次：" + applicationContext.getBean(SingletonScopeBean.class));
    }

    @Autowired
    private PrototypeScopeBean prototypeScopeBean;

    @GetMapping("/prototype")
    public void prototype() {
        System.out.println("prototype @Autowired第一次：" + prototypeScopeBean);
        System.out.println("prototype @Autowired第二次：" + prototypeScopeBean);
        System.out.println("prototype getBean第一次：" + applicationContext.getBean(PrototypeScopeBean.class));
        System.out.println("prototype getBean第二次：" + applicationContext.getBean(PrototypeScopeBean.class));
    }

    @GetMapping("/request")
    public void request() {
        System.out.println("主线程request第一次：" + applicationContext.getBean(RequestScopeBean.class));
        System.out.println("主线程request第二次：" + applicationContext.getBean(RequestScopeBean.class));

        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        new Thread(() -> {
            RequestContextHolder.setRequestAttributes(attributes, true);
            System.out.println("子线程request：" + applicationContext.getBean(RequestScopeBean.class));
        }).start();
    }

    @GetMapping("/session")
    public void session(HttpServletRequest request) {
        System.out.println("主线程sessionInfo: " + request.getSession());
        System.out.println("主线程session第一次：" + applicationContext.getBean(SessionScopeBean.class));
        System.out.println("主线程session第二次：" + applicationContext.getBean(SessionScopeBean.class));
        // 这里失效session，子线程中就是新的sessionInfo了
        request.getSession().invalidate();

        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        new Thread(() -> {
            RequestContextHolder.setRequestAttributes(attributes, true);

            System.out.println("子线程sessionInfo: " + request.getSession());
            System.out.println("子线程session第一次：" + applicationContext.getBean(SessionScopeBean.class));
            System.out.println("子线程session第二次：" + applicationContext.getBean(SessionScopeBean.class));
        }).start();
    }

    @GetMapping("/application")
    public void application() {
        System.out.println("主线程application第一次：" + applicationContext.getBean(ApplicationScopeBean.class));
        System.out.println("主线程application第二次：" + applicationContext.getBean(ApplicationScopeBean.class));

        new Thread(() -> {
            System.out.println("子线程application第一次：" + applicationContext.getBean(ApplicationScopeBean.class));
            System.out.println("子线程application第二次：" + applicationContext.getBean(ApplicationScopeBean.class));
        }).start();
    }

    @Autowired
    private SimpleThreadScopeBean simpleThreadScopeBean;

    @GetMapping("/simpleThreadScope")
    public void simpleThreadScope() {
        System.out.println("主线程依赖注入第一次：" + simpleThreadScopeBean);
        System.out.println("主线程依赖注入第二次：" + simpleThreadScopeBean);
        System.out.println("主线程依赖查找第一次：" + applicationContext.getBean(SimpleThreadScopeBean.class));
        System.out.println("主线程依赖查找第二次：" + applicationContext.getBean(SimpleThreadScopeBean.class));

        new Thread(() -> {
            System.out.println("子线程依赖注入第一次：" + simpleThreadScopeBean);
            System.out.println("子线程依赖注入第二次：" + simpleThreadScopeBean);
            System.out.println("子线程依赖查找第一次：" + applicationContext.getBean(SimpleThreadScopeBean.class));
            System.out.println("子线程依赖查找第二次：" + applicationContext.getBean(SimpleThreadScopeBean.class));
        }).start();
    }

    @Autowired
    private InheritableThreadScopeBean inheritableThreadScopeBean;

    @GetMapping("/inheritableThreadScope")
    public void inheritableThreadScope() {
        System.out.println("主线程依赖注入第一次：" + inheritableThreadScopeBean);
        System.out.println("主线程依赖注入第二次：" + inheritableThreadScopeBean);
        System.out.println("主线程依赖查找第一次：" + applicationContext.getBean(InheritableThreadScopeBean.class));
        System.out.println("主线程依赖查找第二次：" + applicationContext.getBean(InheritableThreadScopeBean.class));

        new Thread(() -> {
            System.out.println("子线程依赖注入第一次：" + inheritableThreadScopeBean);
            System.out.println("子线程依赖注入第二次：" + inheritableThreadScopeBean);
            System.out.println("子线程依赖查找第一次：" + applicationContext.getBean(InheritableThreadScopeBean.class));
            System.out.println("子线程依赖查找第二次：" + applicationContext.getBean(InheritableThreadScopeBean.class));
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }
}
