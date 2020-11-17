package com.sunchaser.sparrow.designpatterns.strategy;

import com.sunchaser.sparrow.designpatterns.strategy.impl.FlyNoWay;
import com.sunchaser.sparrow.designpatterns.strategy.impl.MuteQuack;

/**
 * @author sunchaser
 * @date 2019/9/15
 * @description 玩具鸭：不会飞也不会叫
 */
public class ToyDuck extends Duck {

    public ToyDuck() {
        // 不会飞的行为
        flyBehavior = new FlyNoWay();
        // 不会叫的行为
        quackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的玩具鸭");
    }
}
