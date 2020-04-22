package com.sunchaser.javase.base.cloneable;

/**
 * 英雄：孙悟空
 * @author sunchaser
 * @date 2020/4/7
 * @since 1.0
 */
public class SunWuKong implements LolHero,Cloneable {

    /**
     * 名字
     */
    private String name = "齐天大圣-孙悟空";

    /**
     * 生命值
     */
    private LifeValue sunWuKongLifeValue;

    /**
     * 法力值
     */
    private Mana sunWuKongMana;

    /* skill */
    private LolSkill Q = new LolSkill("Q","粉碎打击","孙悟空的下次攻击造成额外物理伤害，获得距离加成，并暂时减少敌人的护甲。");
    private LolSkill W = new LolSkill("W","真假猴王","孙悟空进入隐形状态片刻，并留下一个替身，替身在片刻后会对其周围的敌人造成魔法伤害。");
    private LolSkill E = new LolSkill("E","腾云突击","孙悟空突进至目标敌人处，并变出最多2个分身，攻击附近目标。对每个击中的目标造成物理伤害。");
    private LolSkill R = new LolSkill("R","大闹天宫","孙悟空展开金箍棒，不停地旋转，对敌人造成伤害并将其击飞。在此期间，孙悟空速度持续增加。");

    @Override
    public Object clone() throws CloneNotSupportedException {
        SunWuKong clone = (SunWuKong) super.clone();
        LifeValue lifeValueClone = (LifeValue) this.sunWuKongLifeValue.clone();
        Mana manaClone = (Mana) this.sunWuKongMana.clone();
        clone.setSunWuKongLifeValue(lifeValueClone);
        clone.setSunWuKongMana(manaClone);
        return clone;
    }

    @Override
    public void attack(LolSkill lolSkill) {
        System.out.println("使用了" + lolSkill + "进行了攻击");
    }

    @Override
    public void beAttacked() {
        Integer oldSunWuKongLifeValue = this.sunWuKongLifeValue.getLifeValue() - 1;
        this.sunWuKongLifeValue.setLifeValue(oldSunWuKongLifeValue);
    }

    public String getName() {
        return name;
    }

    public LifeValue getSunWuKongLifeValue() {
        return sunWuKongLifeValue;
    }

    public SunWuKong setSunWuKongLifeValue(LifeValue sunWuKongLifeValue) {
        this.sunWuKongLifeValue = sunWuKongLifeValue;
        return this;
    }

    public Mana getSunWuKongMana() {
        return sunWuKongMana;
    }

    public SunWuKong setSunWuKongMana(Mana sunWuKongMana) {
        this.sunWuKongMana = sunWuKongMana;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SunWuKong{");
        sb.append("name='").append(name).append('\'');
        sb.append(", sunWuKongLifeValue=").append(sunWuKongLifeValue);
        sb.append(", sunWuKongMana=").append(sunWuKongMana);
        sb.append('}');
        return sb.toString();
    }
}
