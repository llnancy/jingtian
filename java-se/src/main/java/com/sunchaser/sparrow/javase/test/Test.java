package com.sunchaser.sparrow.javase.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author sunchaser
 * @since JDK8 2019/12/5
 *
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(LocalDate.now().toString());
        System.out.println(LocalDate.now().minus(7, ChronoUnit.DAYS));
        System.out.println(LocalDate.now().minusMonths(3));
    }
}
