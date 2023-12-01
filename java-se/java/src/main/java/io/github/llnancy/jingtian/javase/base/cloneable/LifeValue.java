package io.github.llnancy.jingtian.javase.base.cloneable;

/**
 * 英雄生命值类
 *
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public class LifeValue implements Cloneable {

    private Integer lifeValue;

    public LifeValue() {
    }

    public LifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
    }

    public Integer getLifeValue() {
        return lifeValue;
    }

    public void setLifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
    }

    @Override
    public String toString() {
        return "LifeValue{" + "lifeValue=" + lifeValue + '}';
    }

    @Override
    public LifeValue clone() {
        try {
            return (LifeValue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
