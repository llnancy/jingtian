package cn.org.lilu.chapter9.case4;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/23
 * @Description: 实现类ImplClassC实现InterfaceA和InterfaceB接口
 */
public class ImplClassC implements InterfaceA, InterfaceB {
    // cn.org.lilu.chapter9.case4.ImplClassC inherits unrelated defaults for hello() from types cn.org.lilu.chapter9.case4.InterfaceA and cn.org.lilu.chapter9.case4.InterfaceB

    /**
     * 如果不重写hello方法，编译器会抛出编译错误：
     * cn.org.lilu.chapter9.case4.ImplClassC inherits unrelated defaults for hello() from
     * types cn.org.lilu.chapter9.case4.InterfaceA and cn.org.lilu.chapter9.case4.InterfaceB
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
