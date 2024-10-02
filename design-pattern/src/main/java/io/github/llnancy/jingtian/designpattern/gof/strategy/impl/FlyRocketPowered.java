package io.github.llnancy.jingtian.designpattern.gof.strategy.impl;

import io.github.llnancy.jingtian.designpattern.gof.strategy.FlyBehavior;

/**
 * 利用火箭动力的飞翔行为
 * @author sunchaser
 * @since JDK8 2019/9/12
 */
public class FlyRocketPowered implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("fly with rocket");
    }
}
