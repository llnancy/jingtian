package io.github.llnancy.jingtian.javase.java17;

/**
 * switch
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/21
 */
public class Switch {

    public static void main(String[] args) {
        Object obj = new Object();
        switch (obj) {
            case null -> System.out.println("Oops");
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case Switch s -> String.format("String %s", s);
            default -> obj.toString();
        }
    }
}
