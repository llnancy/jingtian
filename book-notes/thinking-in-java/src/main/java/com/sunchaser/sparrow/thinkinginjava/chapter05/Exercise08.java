package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * @author sunchaser
 * @date 2020/1/10
 * @description
 * 练习8：编写具有两个方法的类，在第一个方法内调用第二个方法两次：
 * 第一次调用时不使用this关键字，第二次调用时使用this关键字--这里是为了验证它是起作用的，不应该在实践中使用该方式。
 * @since 1.0
 */
public class Exercise08 {
    public void foo1() {
        System.out.println("foo1");
        foo2();
        this.foo2();
    }
    public void foo2() {
        System.out.println("foo2");
    }

    public static void main(String[] args) {
        Exercise08 exercise08 = new Exercise08();
        exercise08.foo1();
    }
}
