package cn.org.lilu.chapter9.case5;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/23
 * @Description: 实现类ImplClassD同时实现InterfaceB和InterfaceC接口
 */
public class ImplClassD implements InterfaceB,InterfaceC {

    /**
     * InterfaceB和InterfaceC接口中都没有hello实现
     * 它们的父接口InterfaceA中才有hello的默认实现
     * 所以输出
     * hello interface A
     *
     * 如果InterfaceB中也提供hello默认实现，根据规则第二点，编译器会选择InterfaceB中声明的默认方法
     *
     * 如果InterfaceB和InterfaceC都提供hello默认实现，就会出现冲突，ImplClassD实现类必须实现hello方法。
     *
     * 如果InterfaceC接口中添加非默认抽象方法hello，那么它比继承InterfaceA接口而来的hello优先级高，
     * 此时实现类ImplClassD必须显式实现InterfaceC的hello抽象方法。
     */
    public static void main(String[] args) {
        new ImplClassD().hello();
    }
}
