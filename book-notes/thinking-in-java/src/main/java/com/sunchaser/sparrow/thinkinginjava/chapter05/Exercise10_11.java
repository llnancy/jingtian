package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * 练习10：编写具有finalize()方法的类，并在方法中打印信息。在main中为该类创建一个对象。试解释这个程序的行为。
 * 行为：创建多个Exercise10对象，多次调用System.gc()方法，并不是每次都会执行两次finalize()方法。
 *
 * 练习11：修改前一个练习的程序，让你的finalize()总会被调用
 * @author sunchaser
 * @since JDK8 2020/1/13
 */
public class Exercise10_11 {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize方法被调用了...");
    }

    /**
     * 分别创建2个对象，然后调用System.gc()方法，发现并不是每次都会执行两次finalize()方法。
     */
    public static void test10() {
        new Exercise10_11();
        System.gc();
        new Exercise10_11();
        System.gc();
    }

    /**
     * 在调用完System.gc()方法后，手动调用System.runFinalization()方法让finalize()每次都会执行。
     */
    public static void main(String[] args) {
         test10();
         System.runFinalization();
    }
}
