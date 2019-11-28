package com.sunchaser.java8.chapter2;

/**
 * @author sunchaser
 * @date 2019/8/12
 * @description
 * 测验2.2：匿名类谜题
 * 代码执行时会有什么样的输出呢，4、5、6还是42？
 */
public class MeaningOfThis {
    public final int value = 4;

    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String[] args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt(); // 输出5 Runnable对象的value值
    }
}
