package com.sunchaser.chapter9.case4;

/**
 * @author: sunchaser
 * @date: 2019/8/23
 * @description: 实现类ImplClassC实现InterfaceA和InterfaceB接口
 */
public class ImplClassC implements InterfaceA, InterfaceB {
    // ImplClassC inherits unrelated defaults for hello() from types InterfaceA and InterfaceB

    /**
     * 如果不重写hello方法，编译器会抛出编译错误：
     * ImplClassC inherits unrelated defaults for hello() from
     * types InterfaceA and InterfaceB
     * 可以直接重新实现hello方法
     * 也可以使用super关键字调用父接口的默认实现。
     *
     */
    @Override
    public void hello() {
        // 可以自己重新写一个实现
        // 也可以调用父接口默认实现
        InterfaceB.super.hello();
    }
}
