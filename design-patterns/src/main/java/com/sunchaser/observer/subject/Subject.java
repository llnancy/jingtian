package com.sunchaser.observer.subject;

import com.sunchaser.observer.observer.Observer;

/**
 * @author: sunchaser
 * @date: 2019/10/17
 * @description: 抽象主题
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
