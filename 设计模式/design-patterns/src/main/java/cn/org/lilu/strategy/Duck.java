package cn.org.lilu.strategy;

/**
 * @Auther: lilu
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

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void performFly() {
        if (flyBehavior != null) {
            flyBehavior.fly();
        }
    }

    public void performQuack() {
        if (quackBehavior != null) {
            quackBehavior.quack();
        }
    }

    public void swim() {
        System.out.println("鸭子会游泳");
    }

    /**
     * 显示鸭子信息的方法
     */
    public abstract void display();
}
