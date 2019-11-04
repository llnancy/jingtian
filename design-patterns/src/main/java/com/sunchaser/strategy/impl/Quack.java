package com.sunchaser.strategy.impl;

import com.sunchaser.strategy.QuackBehavior;

/**
 * @author: sunchaser
 * @date: 2019/9/12
 * @description: 嘎嘎叫的行为
 */
public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack 嘎嘎叫");
    }
}
