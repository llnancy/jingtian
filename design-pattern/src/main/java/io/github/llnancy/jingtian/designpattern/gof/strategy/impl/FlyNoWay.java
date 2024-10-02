package io.github.llnancy.jingtian.designpattern.gof.strategy.impl;

import io.github.llnancy.jingtian.designpattern.gof.strategy.FlyBehavior;

/**
 * 不能飞的行为
 * @author sunchaser
 * @since JDK8 2019/9/12
 */
public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("can't fly");
    }
}
