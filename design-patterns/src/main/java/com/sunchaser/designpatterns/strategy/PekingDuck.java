package com.sunchaser.designpatterns.strategy;

import com.sunchaser.designpatterns.strategy.impl.FlyNoWay;
import com.sunchaser.designpatterns.strategy.impl.Squeak;

/**
 * @author sunchaser
 * @date 2019/9/15
 * @description 北京鸭：不能飞翔，吱吱叫
 */
public class PekingDuck extends Duck {

    public PekingDuck() {
        // 不能飞的行为
        flyBehavior = new FlyNoWay();
        // 吱吱叫的行为
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的北京鸭");
    }
}
