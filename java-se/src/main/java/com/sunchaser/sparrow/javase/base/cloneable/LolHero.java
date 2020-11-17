package com.sunchaser.sparrow.javase.base.cloneable;

/**
 * LOL英雄接口
 * @author sunchaser
 * @date 2020/4/7
 * @since 1.0
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
