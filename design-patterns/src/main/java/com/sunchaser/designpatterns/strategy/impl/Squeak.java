package com.sunchaser.designpatterns.strategy.impl;

import com.sunchaser.designpatterns.strategy.QuackBehavior;

/**
 * @author sunchaser
 * @date 2019/9/12
 * @description 吱吱叫的行为
 */
public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Squeak 吱吱叫");
    }
}
