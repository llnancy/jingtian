package com.sunchaser.strategy.impl;

import com.sunchaser.strategy.FlyBehavior;

/**
 * @author: sunchaser
 * @date: 2019/9/12
 * @description: 利用火箭动力的飞翔行为
 */
public class FlyRocketPowered implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("fly with rocket");
    }
}
