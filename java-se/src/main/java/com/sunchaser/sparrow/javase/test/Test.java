package com.sunchaser.sparrow.javase.test;

/**
 * @author sunchaser
 * @since JDK8 2019/12/5
 *
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(tryFinally());
    }

    private static int tryFinally() {
        try {
            System.out.println("try...");
            System.exit(0);
            return 1;
        } finally {
            System.out.println("finally...");
        }
    }
}
