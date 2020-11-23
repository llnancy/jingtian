package com.sunchaser.sparrow.designpatterns.strategy.impl;

import com.sunchaser.sparrow.designpatterns.strategy.QuackBehavior;

/**
 * 吱吱叫的行为
 * @author sunchaser
 * @since JDK8 2019/9/12
 */
public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Squeak 吱吱叫");
    }
}
