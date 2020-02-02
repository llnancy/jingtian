package com.sunchaser.thinkingInJava.chapter05;

/**
 * @author sunchaser
 * @date 2020/2/2
 * @description
 * 练习13：无论是通过标为（1）的那行代码访问静态的cup1对象，还是把标为（1）的行注释掉，
 * 让它去运行标为（2）的那行代码（即解除标为（2）的行的注释），Cups的静态初始化动作都会得到执行。
 * 如果把标为（1）和（2）的行同时注释掉，Cups的静态初始化动作就不会进行，就像在输出中看到的那样。
 * 此外，激活一行还是两行标为（2）的代码（即解除注释）都无关紧要，静态初始化动作只进行一次。
 * @since 1.0
 */
public class Exercise13 {
    public static void main(String[] args) {
        System.out.println("inside main()");
        Cups.cup1.f(99); // (1)
    }
//    static Cups cups1 = new Cups(); // (2)
//    static Cups cups2 = new Cups(); // (2)
}

class Cup {
    public Cup(int marker) {
        System.out.println("Cup(" + marker + ")");
    }

    public void f(int marker) {
        System.out.println("f(" + marker + ")");
    }
}

class Cups {
    static Cup cup1;
    static Cup cup2;
    static {
        cup1 = new Cup(1);
        cup2 = new Cup(2);
    }

    public Cups() {
        System.out.println("Cups");
    }
}
