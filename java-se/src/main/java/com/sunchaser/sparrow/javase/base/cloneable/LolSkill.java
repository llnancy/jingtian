package com.sunchaser.sparrow.javase.base.cloneable;

/**
 * LOL英雄的技能类
 * @author sunchaser
 * @date 2020/4/7
 * @since 1.0
 */
public class LolSkill {
    private String key;
    private String name;
    private String desc;

    public LolSkill() {
    }

    public LolSkill(String key, String name, String desc) {
        this.key = key;
        this.name = name;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LolSkill{");
        sb.append("key='").append(key).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
