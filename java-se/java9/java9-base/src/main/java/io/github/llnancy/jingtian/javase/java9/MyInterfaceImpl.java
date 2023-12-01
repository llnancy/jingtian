package io.github.llnancy.jingtian.javase.java9;

/**
 * interface impl
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public class MyInterfaceImpl implements MyInterface {

    @Override
    public void abstractMethod() {
        System.out.println("实现类重写接口中的抽象方法");
    }

    @Override
    public void defaultMethod() {
        MyInterface.super.defaultMethod();
        System.out.println("实现类重写接口中的默认方法");
    }

    public static void main(String[] args) {
        // 接口中的静态方法只能通过接口进行调用
        MyInterface.staticMethod();
        // 实现类未继承接口中的静态方法，无法调用
        // MyInterfaceImpl.staticMethod();

        MyInterface impl = new MyInterfaceImpl();
        impl.defaultMethod();

        // 接口的私用方法，不能在接口外部被调用
        // impl.privateMethod();
    }
}
