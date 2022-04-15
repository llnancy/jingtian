package com.sunchaser.sparrow.thinkinginjava.chapter06;

import com.sunchaser.sparrow.thinkinginjava.chapter06.access.dessert.Cookie;

/**
 * @author sunchaser
 * @since JDK8 2020/2/3
 */
public class Dinner {
    public static void main(String[] args) {
        Cookie x = new Cookie();
        // 'bite()' is not public in '...access.dessert.Cookie'.
        // Cannot be accessed from outside package
        // x.bite();
    }
}
