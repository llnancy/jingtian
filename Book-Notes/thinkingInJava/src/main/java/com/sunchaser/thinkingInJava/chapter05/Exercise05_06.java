package com.sunchaser.thinkingInJava.chapter05;

/**
 * @author sunchaser
 * @date 2020/1/8
 * @description
 * 练习5：创建一个名为Dog的类，它具有重载的bark()方法。此方法应根据不同的基本数据类型重载，并根据被调用的版本，
 * 打印出不同类型的狗吠（barking）、咆哮（howling）等信息。编写main方法来调用所有不同版本的方法。
 *
 * 练习6：修改前一个练习的程序，让两个重载方法各自接受两个类型不同的参数，但两者顺序相反。验证其是否工作。
 * @since 1.0
 */
public class Exercise05_06 {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.bark(1);
        dog.bark(1.0d);
        dog.bark(1,1.0d);
        dog.bark(1.0d,1);
    }
}

class Dog {
    public void bark(int i) {
        System.out.println("int类型：狗吠（barking）");
    }

    public void bark(double d) {
        System.out.println("double类型：咆哮（howling）");
    }

    public void bark(int i,double d) {
        System.out.println("int-double类型：狗吠（barking）");
    }

    public void bark(double d,int i) {
        System.out.println("double-int类型：咆哮（howling）");
    }
}
