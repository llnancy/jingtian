package io.github.llnancy.jingtian.javase.java8.optional;

import lombok.Data;

import java.util.Optional;

/**
 * test {@link Optional}
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/17
 */
public class OptionalTest {

    public static void main(String[] args) {
        String val = getVal();
        String value = Optional.ofNullable(val)
                .orElse("Default value");
        Bean bean = getBean();
        String id = Optional.ofNullable(bean)
                .map(Bean::getId)
                .orElse("Default id");
        System.out.println(value);
        System.out.println(id);
    }

    private static String getVal() {
        return null;
    }

    private static Bean getBean() {
        return null;
    }

    @Data
    static class Bean {

        private String id;

        private String name;
    }
}
