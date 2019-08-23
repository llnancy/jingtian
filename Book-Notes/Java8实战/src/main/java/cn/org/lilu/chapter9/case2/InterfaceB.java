package cn.org.lilu.chapter9.case2;

import cn.org.lilu.chapter9.case1.InterfaceA;

/**
 * @Auther: lilu
 * @Date: 2019/8/23
 * @Description: 接口B 继承 接口A，提供自己的默认hello方法
 */
public interface InterfaceB extends InterfaceA {
    default void hello() {
        System.out.println("hello interface B");
    }
}
