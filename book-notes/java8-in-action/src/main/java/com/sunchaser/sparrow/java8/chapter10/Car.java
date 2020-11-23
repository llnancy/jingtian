package com.sunchaser.sparrow.java8.chapter10;

import java.util.Optional;

/**
 * 车 类
 * @author sunchaser
 * @since JDK8 2019/8/25
 */
public class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
