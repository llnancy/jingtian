package cn.org.lilu.strategy.traditional;

/**
 * @Auther: lilu
 * @Date: 2019/9/15
 * @Description: 北京鸭：不会飞翔
 */
public class PekingDuck extends Duck {
    @Override
    public void display() {
        System.out.println("这是一只北京鸭");
    }

    @Override
    public void fly() {
        System.out.println("北京鸭不会飞翔");
    }
}
