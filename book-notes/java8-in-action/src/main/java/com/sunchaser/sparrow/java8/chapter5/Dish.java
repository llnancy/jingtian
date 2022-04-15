package com.sunchaser.sparrow.java8.chapter5;

/**
 * 菜品类
 * @author sunchaser
 * @since JDK8 2019/8/16
 */
public class Dish {

    /**
     * 菜名
     */
    private final String name;

    /**
     * 是否是素菜
     */
    private final boolean vegetarian;

    /**
     * 菜的热量
     */
    private final int calories;

    /**
     * 菜的类型
     */
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public enum Type {
        // 荤菜
        MEAT,
        FISH,
        OTHER
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vegetarian=" + vegetarian +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }
}
