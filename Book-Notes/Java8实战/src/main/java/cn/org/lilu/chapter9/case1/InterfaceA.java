package cn.org.lilu.chapter9.case1;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/23
 * @Description: 接口A
 */
public interface InterfaceA {
    default void hello() {
        System.out.println("hello interface A");
    }
}
