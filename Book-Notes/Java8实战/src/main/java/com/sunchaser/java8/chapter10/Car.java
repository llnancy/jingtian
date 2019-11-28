package com.sunchaser.java8.chapter10;

import java.util.Optional;

/**
 * @author sunchaser
 * @date 2019/8/25
 * @description 车 类
 */
public class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
