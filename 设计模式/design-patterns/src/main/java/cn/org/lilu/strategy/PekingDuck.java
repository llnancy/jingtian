package cn.org.lilu.strategy;

import cn.org.lilu.strategy.impl.FlyNoWay;
import cn.org.lilu.strategy.impl.Squeak;

/**
 * @Auther: lilu
 * @Date: 2019/9/15
 * @Description: 北京鸭：不能飞翔，吱吱叫
 */
public class PekingDuck extends Duck {

    public PekingDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("这是一只使用策略模式的北京鸭");
    }
}
