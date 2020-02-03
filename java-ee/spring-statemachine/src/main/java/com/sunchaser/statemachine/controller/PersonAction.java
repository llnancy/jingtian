package com.sunchaser.statemachine.controller;

import com.sunchaser.statemachine.pojo.Person;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author sunchaser
 * @date 2019/9/24
 * @description
 */
public class PersonAction {
    private PersonAction() {
    }

    private static final Integer jumpHeight = 6;

    public static void jump(Person person,String input) throws Exception {
        Assert.notNull(person,"请指定某个人物...");
        if (Objects.equals(input,"Space")) {
            person.setHeightToFloor(person.getHeightToFloor() + jumpHeight);
            System.out.println("人物进行了跳的动作,距离地面的高度为：" + person.getHeightToFloor());
        }
    }
}
