package com.sunchaser.sparrow.designpatterns.observer.observer;

import com.sunchaser.sparrow.designpatterns.observer.subject.Subject;

/**
 * 抽象观察者
 * @author sunchaser
 * @since JDK8 2019/10/17
 */
public interface Observer {

    /**
     * 观察者收到主题通知后作出的响应
     * @param subject 主题，观察者可知道是哪个主题发出的通知
     * @param args 可变参数，可以从主题传递参数到观察者
     */
    void response(Subject subject, Object ... args);
}