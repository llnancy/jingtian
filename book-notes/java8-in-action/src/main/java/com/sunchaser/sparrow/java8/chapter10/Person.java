package com.sunchaser.sparrow.java8.chapter10;

import java.util.Optional;

/**
 * 人 类
 * @author sunchaser
 * @since JDK8 2019/8/25
 */
public class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}
