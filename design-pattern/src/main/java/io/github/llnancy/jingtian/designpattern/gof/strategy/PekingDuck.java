package io.github.llnancy.jingtian.designpattern.gof.strategy;

import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.FlyNoWay;
import io.github.llnancy.jingtian.designpattern.gof.strategy.impl.Squeak;

/**
 * 北京鸭：不能飞翔，吱吱叫
 * @author sunchaser
 * @since JDK8 2019/9/15
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
