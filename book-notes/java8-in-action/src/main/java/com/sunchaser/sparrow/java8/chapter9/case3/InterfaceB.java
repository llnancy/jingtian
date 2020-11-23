package com.sunchaser.sparrow.java8.chapter9.case3;

import com.sunchaser.sparrow.java8.chapter9.case1.InterfaceA;

/**
 * 接口B 继承 接口A，提供自己的默认hello方法
 * @author sunchaser
 * @since JDK8 2019/8/23
 */
public interface InterfaceB extends InterfaceA {
    default void hello() {
        System.out.println("hello interface B");
    }
}
