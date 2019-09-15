package cn.org.lilu.strategy.impl;

import cn.org.lilu.strategy.FlyBehavior;

/**
 * @Auther: lilu
 * @Date: 2019/9/12
 * @Description: 不能飞的行为
 */
public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("can't fly");
    }
}
