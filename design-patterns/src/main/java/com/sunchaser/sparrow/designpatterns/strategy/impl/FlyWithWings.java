package com.sunchaser.sparrow.designpatterns.strategy.impl;

import com.sunchaser.sparrow.designpatterns.strategy.FlyBehavior;

/**
 * 可以飞的行为
 * @author sunchaser
 * @since JDK8 2019/9/12
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("fly with wings");
    }
}
