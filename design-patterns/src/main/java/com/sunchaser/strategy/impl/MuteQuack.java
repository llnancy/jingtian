package com.sunchaser.strategy.impl;

import com.sunchaser.strategy.QuackBehavior;

/**
 * @author: sunchaser
 * @date: 2019/9/12
 * @description: 不会叫的行为
 */
public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Silence 不会叫");
    }
}
