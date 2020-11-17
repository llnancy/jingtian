package com.sunchaser.sparrow.javase.base.cloneable;

/**
 * 英雄生命值类
 * @author sunchaser
 * @date 2020/4/7
 * @since 1.0
 */
public class LifeValue implements Cloneable {
    private Integer lifeValue;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public LifeValue() {
    }

    public LifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
    }

    public Integer getLifeValue() {
        return lifeValue;
    }

    public LifeValue setLifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LifeValue{");
        sb.append("lifeValue=").append(lifeValue);
        sb.append('}');
        return sb.toString();
    }
}
