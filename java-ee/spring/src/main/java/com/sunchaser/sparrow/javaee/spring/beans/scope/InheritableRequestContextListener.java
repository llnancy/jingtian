package com.sunchaser.sparrow.javaee.spring.beans.scope;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/1
 */
@Component
public class InheritableRequestContextListener extends RequestContextListener implements WebApplicationInitializer {
    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE =
            InheritableRequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

    @Override
    public void requestInitialized(@NonNull ServletRequestEvent requestEvent) {
        if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException(
                    "Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
        }
        HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
        LocaleContextHolder.setLocale(request.getLocale());
        RequestContextHolder.setRequestAttributes(attributes, true);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        DispatcherServlet servlet = new DispatcherServlet((WebApplicationContext) servletContext);
        servlet.setThreadContextInheritable(true);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcherServlet", servlet);
    }
}
