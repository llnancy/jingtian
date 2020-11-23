package com.sunchaser.sparrow.designpatterns.observer.subject;

import com.sunchaser.sparrow.designpatterns.observer.observer.Observer;

/**
 * 抽象主题
 * @author sunchaser
 * @since JDK8 2019/10/17
 */
public interface Subject {

    /**
     * 向主题注册观察者
     * @param observer 观察者对象
     */
    void registerObserver(Observer observer);

    /**
     * 从主题中移除观察者
     * @param observer 观察者对象
     */
    void removeObserver(Observer observer);

    /**
     * 当主题状态改变时，通知所有的观察者
     */
    void notifyObservers();
}
