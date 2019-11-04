package com.sunchaser.observer.observer;

import com.sunchaser.observer.subject.Subject;

import java.util.Arrays;

/**
 * @author: sunchaser
 * @date: 2019/10/17
 * @description:
 */
public class SunflowerObserver implements Observer {
    /**
     * 观察者收到主题通知后作出的响应
     *
     * @param subject 主题，观察者可知道是哪个主题发出的通知
     * @param args    可变参数，可以从主题传递参数到观察者
     */
    @Override
    public void response(Subject subject, Object... args) {
        System.out.println(this.getClass().getSimpleName() + "收到来自" + subject.getClass().getSimpleName() +"主题的通知。");
        Arrays.stream(args).map(String::valueOf).forEach(System.out::println);
    }
}
