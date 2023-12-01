package io.github.llnancy.jingtian.javase.base.cloneable;

/**
 * 英雄法力值类
 *
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public class Mana implements Cloneable {

    private Integer mana;

    public Mana() {
    }

    public Mana(Integer mana) {
        this.mana = mana;
    }

    public Integer getMana() {
        return mana;
    }

    public void setMana(Integer mana) {
        this.mana = mana;
    }

    @Override
    public String toString() {
        return "Mana{" + "mana=" + mana + '}';
    }

    @Override
    public Mana clone() {
        try {
            return (Mana) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
