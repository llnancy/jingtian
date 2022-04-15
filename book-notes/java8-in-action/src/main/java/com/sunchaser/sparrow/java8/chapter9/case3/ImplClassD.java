package com.sunchaser.sparrow.java8.chapter9.case3;

import com.sunchaser.sparrow.java8.chapter9.case1.InterfaceA;

/**
 * 实现类ImplClassD实现了InterfaceA接口，并重写了接口的hello方法。
 * @author sunchaser
 * @since JDK8 2019/8/23
 */
public class ImplClassD implements InterfaceA {
    @Override
    public void hello() {
        System.out.println("hello implements D");
    }
}
