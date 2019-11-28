package com.sunchaser.designpatterns.strategy.traditional;

/**
 * @author sunchaser
 * @date 2019/9/15
 * @description 野鸭：嘎嘎叫、会游泳和飞翔
 */
public class WildDuck extends Duck {
    @Override
    public void display() {
        System.out.println("这是一只野鸭");
    }
}
