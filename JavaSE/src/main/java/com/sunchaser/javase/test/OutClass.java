package com.sunchaser.javase.test;

/**
 * @auther: sunchaser
 * @date 2019/10/22
 * @description
 * @since 1.0
 */
public class OutClass {
    int a ;

    public void test() {
        a = 1;
    }

    public InnerClass newInnerClass() {
        return new InnerClass();
    }

    public class InnerClass {
        public void test() {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        OutClass outClass = new OutClass();
        outClass.test();
        InnerClass innerClass = outClass.newInnerClass();
        innerClass.test();
    }
}
