package io.github.llnancy.jingtian.designpattern.gof.strategy;

import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.FlyWithWings;
import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.Quack;

/**
 * 野鸭：具有可以飞的行为和嘎嘎叫的行为。
 * @author sunchaser
 * @since JDK8 2019/9/15
 */
public class WildDuck extends Duck {

    public WildDuck() {
        // 随风飞翔行为
        flyBehavior = new FlyWithWings();
        // 嘎嘎叫行为
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的野鸭");
    }
}
