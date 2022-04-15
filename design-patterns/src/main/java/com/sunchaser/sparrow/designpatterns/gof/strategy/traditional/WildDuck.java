package com.sunchaser.sparrow.designpatterns.gof.strategy.traditional;

/**
 * 野鸭：嘎嘎叫、会游泳和飞翔
 * @author sunchaser
 * @since JDK8 2019/9/15
 */
public class WildDuck extends Duck {
    @Override
    public void display() {
        System.out.println("这是一只野鸭");
    }
}
