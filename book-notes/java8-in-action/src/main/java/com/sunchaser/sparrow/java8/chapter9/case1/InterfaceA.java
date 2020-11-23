package com.sunchaser.sparrow.java8.chapter9.case1;

/**
 * 接口A
 * @author sunchaser
 * @since JDK8 2019/8/23
 */
public interface InterfaceA {
    default void hello() {
        System.out.println("hello interface A");
    }
}
