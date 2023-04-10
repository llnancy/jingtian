package io.github.llnancy.jingtian.javase.java9;

import java.util.List;
import java.util.stream.Stream;

/**
 * Stream API
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public class StreamAPI {

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 3, 5, 7, 9);

        System.out.println("===================takeWhile===================");
        // takeWhile：1, 3
        list.stream().takeWhile(el -> el < 5).forEach(System.out::println);

        System.out.println("===================dropWhile===================");
        // dropWhile：5, 7, 9
        list.stream().dropWhile(el -> el < 5).forEach(System.out::println);

        System.out.println("===================ofNullable===================");
        Stream<Object> stream = Stream.ofNullable(null);
        System.out.println(stream.count());// 0

        System.out.println("===================iterate===================");
        // Java8 的终止方式
        Stream.iterate(1, i -> i + 1).limit(5).forEach(System.out::println);
        // Java9 的终止方式
        Stream.iterate(1, i -> i <= 5, i -> i + 1).forEach(System.out::println);
    }
}
