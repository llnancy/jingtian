package com.sunchaser.sparrow.javase.java9;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public interface MyInterface {
    /**
     * 默认被public static final修饰
     */
    String NAME = "interface";

    /**
     * 抽象方法
     */
    void abstractMethod();

    /**
     * Java8的默认方法
     */
    default void defaultMethod() {
        System.out.println("我是Java8接口中的默认方法");

        // since java9
        privateMethod();
    }

    /**
     * Java8的静态方法，默认被public修饰
     */
    static void staticMethod() {
        System.out.println("我是Java8接口中的静态方法");
    }

    /**
     * Java9的私有方法
     */
    private void privateMethod() {
        System.out.println("我是Java9接口中的私有方法");
    }
}
