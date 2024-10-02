package io.github.llnancy.jingtian.designpattern.gof.strategy;

import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.FlyNoWay;
import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.MuteQuack;

/**
 * 玩具鸭：不会飞也不会叫
 * @author sunchaser
 * @since JDK8 2019/9/15
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
