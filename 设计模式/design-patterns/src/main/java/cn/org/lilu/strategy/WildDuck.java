package cn.org.lilu.strategy;

import cn.org.lilu.strategy.impl.FlyWithWings;
import cn.org.lilu.strategy.impl.Quack;

/**
 * @Auther: lilu
 * @Date: 2019/9/15
 * @Description: 野鸭：具有可以飞的行为和嘎嘎叫的行为。
 */
public class WildDuck extends Duck {

    public WildDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的野鸭");
    }
}
