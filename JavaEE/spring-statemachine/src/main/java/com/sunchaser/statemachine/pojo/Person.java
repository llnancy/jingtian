package com.sunchaser.statemachine.pojo;

/**
 * @author sunchaser
 * @date 2019/9/24
 * @description
 */
public class Person {
    private String name;
    private Integer heightToFloor;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getHeightToFloor() {
        return heightToFloor;
    }

    public Person setHeightToFloor(Integer heightToFloor) {
        this.heightToFloor = heightToFloor;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", heightToFloor=" + heightToFloor +
                '}';
    }
}
