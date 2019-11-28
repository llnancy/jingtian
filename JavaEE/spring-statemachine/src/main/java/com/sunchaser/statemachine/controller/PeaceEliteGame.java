package com.sunchaser.statemachine.controller;

import com.sunchaser.statemachine.pojo.Person;

/**
 * @author sunchaser
 * @date 2019/9/24
 * @description
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
