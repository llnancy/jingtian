package cn.org.lilu.strategy;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/12
 * @Description: 使用策略模式的鸭子类
 */
public abstract class Duck {

    /**
     * 飞翔的行为
     */
    FlyBehavior flyBehavior;

    /**
     * 叫的行为
     */
    QuackBehavior quackBehavior;

    /**
     * 设置飞行行为
     * @param flyBehavior 飞行行为
     */
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    /**
     * 设置叫的行为
     * @param quackBehavior 叫行为
     */
    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    /**
     * 使鸭子飞
     */
    public void performFly() {
        if (flyBehavior != null) {
            flyBehavior.fly();
        }
    }

    /**
     * 使鸭子叫
     */
    public void performQuack() {
        if (quackBehavior != null) {
            quackBehavior.quack();
        }
    }

    /**
     * 鸭子游泳的方法
     */
    public void swim() {
        System.out.println("鸭子会游泳");
    }

    /**
     * 显示鸭子信息的方法
     */
    public abstract void display();
}
