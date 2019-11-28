package com.sunchaser.java8.chapter9.case3;

import com.sunchaser.java8.chapter9.case1.InterfaceA;

/**
 * @author sunchaser
 * @date 2019/8/23
 * @description 实现类ImplClassD实现了InterfaceA接口，并重写了接口的hello方法。
 */
public class ImplClassD implements InterfaceA {
    @Override
    public void hello() {
        System.out.println("hello implements D");
    }
}
