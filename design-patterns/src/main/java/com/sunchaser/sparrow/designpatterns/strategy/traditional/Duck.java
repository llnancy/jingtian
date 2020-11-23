package com.sunchaser.sparrow.designpatterns.strategy.traditional;

/**
 * 传统鸭子问题
 *
 * 1. 其它鸭子都继承了Duck类，所以fly方法让所有的子类鸭子都会飞，但并不是所有鸭子都会飞。
 * 2. 实际上这是继承带来的问题，对父类的局部改动，会影响所有子类。称为溢出效应。
 * 3. 为了解决继承带来的问题，我们可以在子类中重写对应的方法来解决。
 * 4. 但是如果有一个玩具鸭类，它需要重写父类所有的方法，这样做的代价有点大。
 * @author sunchaser
 * @since JDK8 2019/9/15
 */
public abstract class Duck {

    /**
     * 显示鸭子信息的方法
     */
    public abstract void display();

    /**
     * 鸭子叫的方法
     */
    public void quack() {
        System.out.println("鸭子嘎嘎叫");
    }

    /**
     * 鸭子游泳的方法
     */
    public void swim() {
        System.out.println("鸭子会游泳");
    }

    /**
     * 鸭子飞翔的方法
     */
    public void fly() {
        System.out.println("鸭子会飞翔");
    }
}
