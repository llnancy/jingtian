package io.github.llnancy.jingtian.javase.java8.interfaces;

/**
 * interface
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/12
 */
public interface MyInterface {

    default void defaultMethod() {
        System.out.println("我是 Java8 接口中的默认方法");
    }

    static void staticMethod() {
        System.out.println("我是 Java8 接口中的静态方法");
    }
}
