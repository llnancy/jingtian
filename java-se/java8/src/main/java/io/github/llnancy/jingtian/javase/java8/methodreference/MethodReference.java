package io.github.llnancy.jingtian.javase.java8.methodreference;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * method reference
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/12
 */
public class MethodReference {

    public static void main(String[] args) {
        // 对象名::成员方法名
        LocalDateTime now = LocalDateTime.now();
        print(now::getYear);

        // 类名::静态方法名
        print(System::currentTimeMillis);

        // 类名::普通方法名
        invoke(String::length, "abc");

        // 类名::new（引用构造函数）
        invoke(String::new, "abc");

        // 类型名[]::new（引用数组的构造函数）
        invoke(String[]::new, 1);

        // lambda 表达式
        print(() -> now.getYear());
        print(() -> System.currentTimeMillis());
        invoke(s -> s.length(), "abc");
        invoke(s -> new String(s), "abc");
        invoke(size -> new String[size], 1);
    }

    private static <T> void print(Supplier<T> supplier) {
        System.out.println(supplier.get());
    }

    private static <T, R> void invoke(Function<T, R> function, T t) {
        System.out.println(function.apply(t));
    }
}
