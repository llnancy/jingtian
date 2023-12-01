package io.github.llnancy.jingtian.javase.collect.reimpllist;

import java.io.Serializable;

/**
 * array list not impl {@link MyList}
 *
 * @author sunchaser
 * @since JDK8 2020/4/27
 */
public class MyArrayListNoImpl extends MyAbstractList implements Cloneable, Serializable {

    private static final long serialVersionUID = -1046037078072580822L;

    @Override
    public void foo() {
        System.out.println("MyArrayListNoImpl#foo");
    }

    @Override
    public MyArrayListNoImpl clone() {
        try {
            return (MyArrayListNoImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
