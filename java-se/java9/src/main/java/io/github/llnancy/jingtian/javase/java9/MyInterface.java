package io.github.llnancy.jingtian.javase.java9;

/**
 * interface
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public interface MyInterface {

    /**
     * 默认被 public static final 修饰
     */
    String NAME = "interface";

    /**
     * 抽象方法
     */
    void abstractMethod();

    /**
     * Java8 的默认方法
     */
    default void defaultMethod() {
        System.out.println("我是 Java8 接口中的默认方法");

        // since Java9
        privateMethod();
    }

    /**
     * Java8 的静态方法，默认被 public 修饰
     */
    static void staticMethod() {
        System.out.println("我是 Java8 接口中的静态方法");
    }

    /**
     * Java9 的私有方法
     */
    private void privateMethod() {
        System.out.println("我是 Java9 接口中的私有方法");
    }
}
