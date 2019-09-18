package cn.org.lilu.chapter9.case1;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/23
 * @Description: 实现类ImplClassC实现InterfaceA接口和InterfaceB接口
 *
 * 决定实现类拥有方法的三条原则
 * 1. 类中的方法优先级最高。类或父类中声明的方法的优先级高于任何声明为默认方法的优先级。
 * 2. 如果无法依据第一条进行判断，那么子接口的优先级更高：
 *    函数签名相同时，优先选择拥有最具体实现的默认方法的接口，
 *    即如果B继承了A，那么B就比A更加具体。
 * 3. 最后，如果还是无法判断，继承了多个接口的类必须通过显式覆盖和调用期望的方法，显式地选择使用哪一个默认方法的实现。
 */
public class ImplClassC implements InterfaceA, InterfaceB {
    /**
     * 实现类ImplClassC中没有hello方法
     * 接口InterfaceB继承了InterfaceA，接口InterfaceB中的hello方法更具体，所以实现类拥有的是InterfaceB中的hello方法。
     */
    public static void main(String[] args) {
        // 输出hello interface B
        new ImplClassC().hello();
    }
}
