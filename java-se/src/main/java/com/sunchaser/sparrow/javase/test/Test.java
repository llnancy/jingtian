package com.sunchaser.sparrow.javase.test;

import java.util.Random;

/**
 * @author sunchaser
 * @since JDK8 2019/12/5
 */
public class Test {

    public static void main(String[] args) {
        Random random = new Random();
        int nextInt = random.nextInt(10);
        System.out.println(nextInt);
    }
}
