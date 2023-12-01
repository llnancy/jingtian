package io.github.llnancy.jingtian.javase.base.cloneable;

/**
 * LOL 英雄的技能类
 *
 * @author sunchaser
 * @since JDK8 2020/4/7
 */
public class LolSkill {

    /**
     * 技能按键
     */
    private String key;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能描述
     */
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
        return "LolSkill{" + "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
