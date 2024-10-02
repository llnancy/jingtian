package io.github.llnancy.jingtian.designpattern.gof.strategy.traditional;

/**
 * 玩具鸭：不会嘎嘎叫、不会游泳和飞翔。
 * @author sunchaser
 * @since JDK8 2019/9/15
 */
public class ToyDuck extends Duck {
    @Override
    public void display() {
        System.out.println("这是一只玩具鸭");
    }

    @Override
    public void quack() {
        System.out.println("玩具鸭不能叫");
    }

    @Override
    public void swim() {
        System.out.println("玩具鸭不会游泳");
    }

    @Override
    public void fly() {
        System.out.println("玩具鸭不会飞翔");
    }
}
