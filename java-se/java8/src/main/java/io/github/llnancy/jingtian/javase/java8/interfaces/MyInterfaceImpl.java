package io.github.llnancy.jingtian.javase.java8.interfaces;

/**
 * interface impl
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/12
 */
public class MyInterfaceImpl implements MyInterface {

    public void test() {
        this.defaultMethod();
    }

    public static void main(String[] args) {
        MyInterface inter = new MyInterfaceImpl();
        // 通过对象实例调用默认方法
        inter.defaultMethod();

        MyInterfaceImpl impl = new MyInterfaceImpl();
        // 实现类中通过 this 关键字调用默认方法
        impl.test();

        // 接口中的静态方法只能通过接口进行调用
        MyInterface.staticMethod();
    }
}
