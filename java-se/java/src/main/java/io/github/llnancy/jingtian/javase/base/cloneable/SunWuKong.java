package io.github.llnancy.jingtian.javase.base.cloneable;

/**
 * 英雄：孙悟空
 *
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public class SunWuKong implements LolHero, Cloneable {

    /**
     * 名字
     */
    private final String name = "齐天大圣-孙悟空";

    /**
     * 生命值
     */
    private LifeValue lifeValue;

    /**
     * 法力值
     */
    private Mana mana;

    /* skill */
    private static final LolSkill Q = new LolSkill("Q", "粉碎打击", "孙悟空的下次攻击造成额外物理伤害，获得距离加成，并暂时减少敌人的护甲。");

    private static final LolSkill W = new LolSkill("W", "真假猴王", "孙悟空进入隐形状态片刻，并留下一个替身，替身在片刻后会对其周围的敌人造成魔法伤害。");

    private static final LolSkill E = new LolSkill("E", "腾云突击", "孙悟空突进至目标敌人处，并变出最多2个分身，攻击附近目标。对每个击中的目标造成物理伤害。");

    private static final LolSkill R = new LolSkill("R", "大闹天宫", "孙悟空展开金箍棒，不停地旋转，对敌人造成伤害并将其击飞。在此期间，孙悟空速度持续增加。");

    @Override
    public Object clone() throws CloneNotSupportedException {
        SunWuKong clone = (SunWuKong) super.clone();
        LifeValue lifeValueClone = this.lifeValue.clone();
        Mana manaClone = this.mana.clone();
        clone.setLifeValue(lifeValueClone);
        clone.setMana(manaClone);
        return clone;
    }

    @Override
    public void attack(LolSkill lolSkill) {
        System.out.println("使用了" + lolSkill + "进行了攻击");
    }

    @Override
    public void beAttacked() {
        Integer oldSunWuKongLifeValue = this.lifeValue.getLifeValue() - 1;
        this.lifeValue.setLifeValue(oldSunWuKongLifeValue);
    }

    public String getName() {
        return name;
    }

    public LifeValue getLifeValue() {
        return lifeValue;
    }

    public void setLifeValue(LifeValue lifeValue) {
        this.lifeValue = lifeValue;
    }

    public Mana getMana() {
        return mana;
    }

    public void setMana(Mana mana) {
        this.mana = mana;
    }

    @Override
    public String toString() {
        return "SunWuKong{" +
                "name='" + name + '\'' +
                ", lifeValue=" + lifeValue +
                ", mana=" + mana +
                '}';
    }
}
