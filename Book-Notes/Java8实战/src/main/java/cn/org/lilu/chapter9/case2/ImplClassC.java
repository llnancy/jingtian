package cn.org.lilu.chapter9.case2;

import cn.org.lilu.chapter9.case1.InterfaceA;
import cn.org.lilu.chapter9.case1.InterfaceB;

/**
 * @Auther: lilu
 * @Date: 2019/8/23
 * @Description: 实现类ImplClassC继承ImplClassD类，同时实现InterfaceA和InterfaceB接口
 */
public class ImplClassC extends ImplClassD implements InterfaceA, InterfaceB {
    /**
     * ImplClassC和ImplClassD类中都未实现hello方法，InterfaceB继承InterfaceA接口，
     * 所以InterfaceB接口中的方法更具体，所以ImplClassC类具有的是InterfaceB中的hello方法。
     *
     * hello interface B
     */
    public static void main(String[] args) {
        new ImplClassC().hello();
    }
}
