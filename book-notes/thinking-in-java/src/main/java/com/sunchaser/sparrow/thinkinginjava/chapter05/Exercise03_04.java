package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * 练习3：创建一个带默认构造器（即无参构造器）的类，在构造器中打印一条消息。为这个类创建一个对象。
 *
 * 练习4：为前一个练习中的类添加一个重载构造器，令其接受一个字符串参数，并在构造器中把你的消息和接受的参数一起打印出来。
 * @author sunchaser
 * @since JDK8 2020/1/8
 */
public class Exercise03_04 {
    public Exercise03_04() {
        System.out.println("无参构造器执行了...");
    }

    public Exercise03_04(String string) {
        System.out.println("重载构造器执行了... 接收的参数为：" + string);
    }

    public static void main(String[] args) {
        // print 无参构造器执行了...
        new Exercise03_04();
        new Exercise03_04("重载构造器");
    }
}
