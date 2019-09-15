package cn.org.lilu.strategy.impl;

import cn.org.lilu.strategy.FlyBehavior;

/**
 * @Auther: lilu
 * @Date: 2019/9/12
 * @Description: 可以飞的行为
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("fly with wings");
    }
}
