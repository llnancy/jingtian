package io.github.llnancy.jingtian.javase.test;

/**
 * @author sunchaser
 * @since JDK8 2019/12/5
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(testFinally());
    }

    private static int testFinally() {
        try {
            int i = 1 / 0;
            return 10;
        } finally {
            return 20;
        }
    }
}
