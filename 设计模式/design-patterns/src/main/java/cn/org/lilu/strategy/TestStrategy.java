package cn.org.lilu.strategy;

import cn.org.lilu.strategy.impl.FlyRocketPowered;

/**
 * @Auther: lilu
 * @Date: 2019/9/12
 * @Description: for test
 */
public class TestStrategy {
    public static void main(String[] args) {
        Duck duck = new PekingDuck();
        duck.performFly();
        duck.performQuack();

        Duck toyDuck = new ToyDuck();
        toyDuck.performFly();
        toyDuck.setFlyBehavior(new FlyRocketPowered());
        toyDuck.performFly();
    }
}
