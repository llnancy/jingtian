package com.sunchaser.sparrow.statemachine.controller;

import com.sunchaser.sparrow.statemachine.pojo.Person;

/**
 * @author sunchaser
 * @since JDK8 2019/9/24
 */
public class PeaceEliteGame {
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.setName("马化腾");
        person.setHeightToFloor(0);
        PersonAction.jump(person,"Space");
        PersonAction.jump(person,"Space");
        PersonAction.jump(person,"Space");
        PersonAction.jump(person,"Space");
        PersonAction.jump(person,"Space");
        PersonAction.jump(person,"Space");
        PersonAction.jump(person,"Space");
    }
}
