package com.sunchaser.sparrow.java8.chapter9.case4;

/**
 * 接口B 未继承
 * @author sunchaser
 * @since JDK8 2019/8/23
 */
public interface InterfaceB {
    default void hello() {
        System.out.println("hello interface B");
    }
}
