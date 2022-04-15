package com.sunchaser.sparrow.javase.java11;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * 局部变量类型推断升级：var上可以添加注解
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK11 2022/2/17
 */
public class UpdateLocalVariableTypeInference {
    public static void main(String[] args) {
        Consumer<String> consumer1 = (var t) -> System.out.println(t.toLowerCase());

        Consumer<String> consumer2 = (@Nullable var t) -> System.out.println(t.toLowerCase());
    }
}
