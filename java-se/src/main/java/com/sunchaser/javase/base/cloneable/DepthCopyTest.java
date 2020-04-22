package com.sunchaser.javase.base.cloneable;

/**
 * 深浅拷贝测试
 * @author sunchaser
 * @date 2020/4/7
 * @since 1.0
 */
public class DepthCopyTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        // 创建一个孙悟空英雄对象，并赋予生命值1000，法力值2000
        SunWuKong sunWuKong = new SunWuKong();
        sunWuKong.setSunWuKongLifeValue(new LifeValue(1000))
                .setSunWuKongMana(new Mana(2000));
        // 克隆一个替身
        SunWuKong clone = (SunWuKong) sunWuKong.clone();
        System.out.println(sunWuKong);
        System.out.println(clone);
        System.out.println(sunWuKong == clone); // false 不是同一个SunWuKong对象
        // 替身受到攻击
        clone.beAttacked();
        System.out.println(sunWuKong);
        System.out.println(clone);
        // false 不是同一个SunWuKong对象；
        // 直接调用super.clone方法时，生命值同时减少
        // 深层拷贝后，原对象生命值不变，替身对象生命值减少。
        System.out.println(sunWuKong == clone);
    }
}
