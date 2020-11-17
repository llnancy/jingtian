package com.sunchaser.sparrow.java8.chapter9.case5;

/**
 * @author sunchaser
 * @date 2019/8/23
 * @description 接口A
 */
public interface InterfaceA {
    default void hello() {
        System.out.println("hello interface A");
    }
}
