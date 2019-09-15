package cn.org.lilu.strategy.impl;

import cn.org.lilu.strategy.QuackBehavior;

/**
 * @Auther: lilu
 * @Date: 2019/9/12
 * @Description: 不会叫的行为
 */
public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Silence 不会叫");
    }
}
