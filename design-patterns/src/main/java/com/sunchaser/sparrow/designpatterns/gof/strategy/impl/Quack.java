package com.sunchaser.sparrow.designpatterns.gof.strategy.impl;

import com.sunchaser.sparrow.designpatterns.gof.strategy.QuackBehavior;

/**
 * 嘎嘎叫的行为
 * @author sunchaser
 * @since JDK8 2019/9/12
 */
public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack 嘎嘎叫");
    }
}
