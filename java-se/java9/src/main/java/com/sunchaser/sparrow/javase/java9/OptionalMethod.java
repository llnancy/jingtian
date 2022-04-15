package com.sunchaser.sparrow.javase.java9;

import java.util.Optional;

/**
 * java9中java.util.Optional类新增多个方法
 * - ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
 * - or(Supplier<? extends Optional<? extends T>> supplier)
 * - stream()
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/17
 */
public class OptionalMethod {
    public static void main(String[] args) {
        Optional<Object> empty = Optional.empty();
        Optional<String> op1 = Optional.of("java");
        Optional<String> op2 = Optional.of("python");

        // ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
        // value非空则执行action；否则执行emptyAction
        empty.ifPresentOrElse(System.out::println, () -> System.out.println("emptyAction"));// emptyAction
        op1.ifPresentOrElse(System.out::println, () -> System.out.println("emptyAction"));// java

        // or(Supplier<? extends Optional<? extends T>> supplier)
        // value非空返回当前Optional对象；否则返回supplier参数提供的Optional对象。
        Optional<Object> or1 = empty.or(() -> op2);
        Optional<String> or2 = op1.or(() -> op2);
        System.out.println(or1);// Optional[python]
        System.out.println(or2);// Optional[java]

        // stream()：将Optional转为Stream
        op1.stream().forEach(System.out::println);// java
    }
}
