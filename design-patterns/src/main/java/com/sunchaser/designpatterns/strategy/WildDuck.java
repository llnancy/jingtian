package com.sunchaser.designpatterns.strategy;

import com.sunchaser.designpatterns.strategy.impl.FlyWithWings;
import com.sunchaser.designpatterns.strategy.impl.Quack;

/**
 * @author sunchaser
 * @date 2019/9/15
 * @description 野鸭：具有可以飞的行为和嘎嘎叫的行为。
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
