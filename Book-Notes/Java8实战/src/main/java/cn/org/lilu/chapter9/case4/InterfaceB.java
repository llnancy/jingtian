package cn.org.lilu.chapter9.case4;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/23
 * @Description: 接口B 未继承
 */
public interface InterfaceB {
    default void hello() {
        System.out.println("hello interface B");
    }
}
