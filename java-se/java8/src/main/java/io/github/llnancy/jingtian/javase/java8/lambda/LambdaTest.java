package io.github.llnancy.jingtian.javase.java8.lambda;

import java.util.Arrays;
import java.util.Comparator;

/**
 * lambda test
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/12
 */
public class LambdaTest {

    public static void main(String[] args) {
        // Runnable 接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("anonymous inner class.");
            }
        }).start();

        // lambda
        new Thread(() -> System.out.println("lambda")).start();

        // Comparator 接口
        Arrays.asList(1, 2, 3).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        // lambda
        Arrays.asList(1, 2, 3).sort((o1, o2) -> o1 - o2);
    }
}
