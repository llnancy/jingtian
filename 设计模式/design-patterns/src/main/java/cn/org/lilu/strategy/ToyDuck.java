package cn.org.lilu.strategy;

import cn.org.lilu.strategy.impl.FlyNoWay;
import cn.org.lilu.strategy.impl.MuteQuack;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/15
 * @Description: 玩具鸭：不会飞也不会叫
 */
public class ToyDuck extends Duck {

    public ToyDuck() {
        // 不会飞的行为
        flyBehavior = new FlyNoWay();
        // 不会叫的行为
        quackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的玩具鸭");
    }
}
