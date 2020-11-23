package com.sunchaser.sparrow.javase.base.cloneable;

/**
 * 英雄法力值类
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public class Mana implements Cloneable {
    private Integer mana;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Mana() {
    }

    public Mana(Integer mana) {
        this.mana = mana;
    }

    public Integer getMana() {
        return mana;
    }

    public Mana setMana(Integer mana) {
        this.mana = mana;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mana{");
        sb.append("mana=").append(mana);
        sb.append('}');
        return sb.toString();
    }
}
