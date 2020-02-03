package com.sunchaser.java8.chapter10;

import java.util.Optional;

/**
 * @author sunchaser
 * @date 2019/8/25
 * @description 人 类
 */
public class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}
