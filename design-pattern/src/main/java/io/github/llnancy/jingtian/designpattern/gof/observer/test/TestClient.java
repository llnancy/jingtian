package io.github.llnancy.jingtian.designpattern.gof.observer.test;

import io.github.llnancy.jingtian.designpattern.gof.observer.observer.Observer;
import io.github.llnancy.jingtian.designpattern.gof.observer.observer.SunchaserObserver;
import io.github.llnancy.jingtian.designpattern.gof.observer.observer.SunflowerObserver;
import io.github.llnancy.jingtian.designpattern.gof.observer.subject.AnchorSubject;
import io.github.llnancy.jingtian.designpattern.gof.observer.subject.Subject;

/**
 * @author sunchaser
 * @since JDK8 2019/10/17
 */
public class TestClient {
    public static void main(String[] args) {
        // 创建主题对象
        Subject subject = new AnchorSubject();
        // 创建 Sunchaser 观察者对象
        Observer sunchaserObserver = new SunchaserObserver();
        // 创建 Sunflower 观察者对象
        Observer sunflowerObserver = new SunflowerObserver();
        // 将观察者注册到主题
        subject.registerObserver(sunchaserObserver);
        subject.registerObserver(sunflowerObserver);
        // 通知 10 次，第 6 次时将 sunflowerObserver 观察者移除
        for (int i = 0; i < 10; i++) {
            subject.notifyObservers();
            if (i == 5) {
                subject.removeObserver(sunflowerObserver);
            }
        }
    }
}
