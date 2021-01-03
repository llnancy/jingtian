package com.sunchaser.sparrow.designpatterns.gof.strategy.impl;

import com.sunchaser.sparrow.designpatterns.gof.strategy.FlyBehavior;

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
