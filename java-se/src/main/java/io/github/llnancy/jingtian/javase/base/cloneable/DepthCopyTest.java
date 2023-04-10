package io.github.llnancy.jingtian.javase.base.cloneable;

import java.util.LinkedList;

/**
 * 深浅拷贝测试
 *
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public class DepthCopyTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        // 创建一个孙悟空英雄对象，并赋予生命值 1000，法力值 2000
        SunWuKong swk = new SunWuKong();
        swk.setLifeValue(new LifeValue(1000));
        swk.setMana(new Mana(2000));
        // 克隆一个替身
        SunWuKong clone = (SunWuKong) swk.clone();
        System.out.println(swk);
        System.out.println(clone);
        System.out.println(swk == clone); // false：不是同一个 SunWuKong 对象
        // 替身受到攻击
        clone.beAttacked();
        System.out.println(swk);
        System.out.println(clone);
        // false：不是同一个 SunWuKong 对象；
        // 直接调用 super.clone 方法时，生命值同时减少
        // 深层拷贝后，原对象生命值不变，替身对象生命值减少。
        System.out.println(swk == clone);

        System.out.println("==============================================");

        // LinkedList 类的 clone

        // 创建一个孙悟空英雄对象，并赋予生命值 1000，法力值 2000
        SunWuKong swk2 = new SunWuKong();
        swk2.setLifeValue(new LifeValue(1000));
        swk2.setMana(new Mana(2000));
        // 克隆一个替身
        SunWuKong swk2Clone = (SunWuKong) swk.clone();
        // 创建 LinkedList 并填充元素
        LinkedList<SunWuKong> list = new LinkedList<>();
        list.add(swk2);
        list.add(swk2Clone);

        // 克隆 LinkedList
        @SuppressWarnings("unchecked")
        LinkedList<SunWuKong> cloneList = (LinkedList<SunWuKong>) list.clone();

        System.out.println("原 list：" + list);
        System.out.println("克隆 list：" + cloneList);
        swk2Clone.beAttacked();
        System.out.println(swk2);
        System.out.println(swk2Clone);
        System.out.println("被攻击后 list：" + list);
        System.out.println("被攻击后克隆 list：" + cloneList);
        list.get(0).beAttacked();
        System.out.println("被攻击后 list：" + list);
        System.out.println("被攻击后克隆 list：" + cloneList);
    }
}
