package com.sunchaser.designpatterns.observer.subject;

import com.google.common.collect.Lists;
import com.sunchaser.designpatterns.observer.observer.Observer;

import java.util.List;

/**
 * @author sunchaser
 * @date 2019/10/17
 * @description 主播主题
 */
public class AnchorSubject implements Subject {

    /**
     * 订阅的观察者集合
     */
    private static List<Observer> observers = Lists.newArrayList();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (!observers.contains(observer)) {
            return;
        }
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.response(this,"携带的参数"));
    }
}
