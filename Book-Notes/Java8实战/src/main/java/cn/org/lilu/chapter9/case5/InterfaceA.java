package cn.org.lilu.chapter9.case5;

/**
 * @Auther: lilu
 * @Date: 2019/8/23
 * @Description: 接口A
 */
public interface InterfaceA {
    default void hello() {
        System.out.println("hello interface A");
    }
}
