package com.sunchaser.sparrow.designpatterns.strategy;

import com.sunchaser.sparrow.designpatterns.strategy.impl.FlyRocketPowered;

/**
 * @author sunchaser
 * @date 2019/9/12
 * @description for test
 */
public class TestStrategy {
    public static void main(String[] args) {
        // 生产一只北京鸭
        Duck duck = new PekingDuck();
        // 让鸭子飞
        duck.performFly();
        // 让鸭子叫
        duck.performQuack();
        // 生产一只玩具鸭
        Duck toyDuck = new ToyDuck();
        // 让鸭子飞：发现不能飞
        toyDuck.performFly();
        // 动态改变行为：让“煮熟的鸭子”飞起来
        toyDuck.setFlyBehavior(new FlyRocketPowered());
        toyDuck.performFly();
    }
}
