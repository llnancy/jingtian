package com.sunchaser.sparrow.java8.chapter9.case3;

import com.sunchaser.sparrow.java8.chapter9.case1.InterfaceA;

/**
 * @author sunchaser
 * @date 2019/8/23
 * @description 接口B 继承 接口A，提供自己的默认hello方法
 */
public interface InterfaceB extends InterfaceA {
    default void hello() {
        System.out.println("hello interface B");
    }
}
