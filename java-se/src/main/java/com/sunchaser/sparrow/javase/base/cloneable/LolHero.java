package com.sunchaser.sparrow.javase.base.cloneable;

/**
 * LOL英雄接口
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public interface LolHero {

    /**
     * 攻击
     * @param lolSkill 使用的技能
     */
    void attack(LolSkill lolSkill);

    /**
     * 受到其它英雄攻击
     */
    void beAttacked();
}
