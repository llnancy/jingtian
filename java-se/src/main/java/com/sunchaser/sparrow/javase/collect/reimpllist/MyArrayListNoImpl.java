package com.sunchaser.sparrow.javase.collect.reimpllist;

import java.io.Serializable;

/**
 * @author sunchaser
 * @date 2020/4/27
 * @since 1.0
 */
public class MyArrayListNoImpl extends MyAbstractList implements Cloneable, Serializable {

    private static final long serialVersionUID = -1046037078072580822L;

    @Override
    public void foo() {
        System.out.println("MyArrayListNoImpl#foo");
    }
}
