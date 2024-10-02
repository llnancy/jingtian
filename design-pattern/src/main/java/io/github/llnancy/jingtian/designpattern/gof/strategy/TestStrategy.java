package io.github.llnancy.jingtian.designpattern.gof.strategy;

import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.FlyRocketPowered;

/**
 * for test
 * @author sunchaser
 * @since JDK8 2019/9/12
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
