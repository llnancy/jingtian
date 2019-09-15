package cn.org.lilu.strategy.impl;

import cn.org.lilu.strategy.QuackBehavior;

/**
 * @Auther: lilu
 * @Date: 2019/9/12
 * @Description: 嘎嘎叫的行为
 */
public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack 嘎嘎叫");
    }
}
