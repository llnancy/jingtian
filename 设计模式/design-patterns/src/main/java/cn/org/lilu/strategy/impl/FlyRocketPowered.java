package cn.org.lilu.strategy.impl;

import cn.org.lilu.strategy.FlyBehavior;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/12
 * @Description: 利用火箭动力的飞翔行为
 */
public class FlyRocketPowered implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("fly with rocket");
    }
}
