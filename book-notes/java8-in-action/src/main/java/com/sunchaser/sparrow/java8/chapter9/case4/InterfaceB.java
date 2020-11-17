package com.sunchaser.sparrow.java8.chapter9.case4;

/**
 * @author sunchaser
 * @date 2019/8/23
 * @description 接口B 未继承
 */
public interface InterfaceB {
    default void hello() {
        System.out.println("hello interface B");
    }
}
