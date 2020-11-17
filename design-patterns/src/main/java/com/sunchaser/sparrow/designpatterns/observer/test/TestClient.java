package com.sunchaser.sparrow.designpatterns.observer.test;

import com.sunchaser.sparrow.designpatterns.observer.observer.Observer;
import com.sunchaser.sparrow.designpatterns.observer.observer.SunchaserObserver;
import com.sunchaser.sparrow.designpatterns.observer.observer.SunflowerObserver;
import com.sunchaser.sparrow.designpatterns.observer.subject.AnchorSubject;
import com.sunchaser.sparrow.designpatterns.observer.subject.Subject;

/**
 * @author sunchaser
 * @date 2019/10/17
 * @description
 */
public class TestClient {
    public static void main(String[] args) {
        // 创建主题对象
        Subject subject = new AnchorSubject();
        // 创建Sunchaser观察者对象
        Observer sunchaserObserver = new SunchaserObserver();
        // 创建Sunflower观察者对象
        Observer sunflowerObserver = new SunflowerObserver();
        // 将观察者注册到主题
        subject.registerObserver(sunchaserObserver);
        subject.registerObserver(sunflowerObserver);
        // 通知10次，第6次时将sunflowerObserver观察者移除
        for (int i = 0; i < 10; i++) {
            subject.notifyObservers();
            if (i == 5) {
                subject.removeObserver(sunflowerObserver);
            }
        }
    }
}
