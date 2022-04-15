package com.sunchaser.sparrow.designpatterns.gof.strategy.impl;

import com.sunchaser.sparrow.designpatterns.gof.strategy.FlyBehavior;

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
