package com.sunchaser.sparrow.designpatterns.gof.observer.subject;

import com.google.common.collect.Lists;
import com.sunchaser.sparrow.designpatterns.gof.observer.observer.Observer;

import java.util.List;

/**
 * 主播主题
 * @author sunchaser
 * @since JDK8 2019/10/17
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
