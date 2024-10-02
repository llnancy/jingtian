package io.github.llnancy.jingtian.designpattern.gof.strategy.impl;

import io.github.llnancy.jingtian.designpattern.gof.strategy.FlyBehavior;

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
