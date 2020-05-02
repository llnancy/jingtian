package com.sunchaser.javase.collect.reimpllist;

import java.io.Serializable;

/**
 * @author sunchaser
 * @date 2020/4/27
 * @since 1.0
 */
public class MyArrayListImpl extends MyAbstractList implements MyList, Cloneable, Serializable {
    private static final long serialVersionUID = 3943903586859431425L;

    @Override
    public void foo() {
        System.out.println("MyArrayListImpl#foo");
    }
}
