package cn.org.lilu.strategy;

import cn.org.lilu.strategy.impl.FlyNoWay;
import cn.org.lilu.strategy.impl.MuteQuack;

/**
 * @Auther: lilu
 * @Date: 2019/9/15
 * @Description: 玩具鸭：不会飞也不会叫
 */
public class ToyDuck extends Duck {

    public ToyDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的玩具鸭");
    }
}
