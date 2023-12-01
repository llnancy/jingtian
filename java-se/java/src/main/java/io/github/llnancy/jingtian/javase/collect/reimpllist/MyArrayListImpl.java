package io.github.llnancy.jingtian.javase.collect.reimpllist;

import java.io.Serializable;

/**
 * array list impl {@link MyList}
 *
 * @author sunchaser
 * @since JDK8 2020/4/27
 */
public class MyArrayListImpl extends MyAbstractList implements MyList, Cloneable, Serializable {

    private static final long serialVersionUID = 3943903586859431425L;

    @Override
    public void foo() {
        System.out.println("MyArrayListImpl#foo");
    }

    @Override
    public MyArrayListImpl clone() {
        try {
            return (MyArrayListImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
