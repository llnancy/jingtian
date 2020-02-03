package com.sunchaser.java8.chapter3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @author sunchaser
 * @date 2019/8/12
 * @description java.util.function包下的函数式接口测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FunctionalTest {

    /**
     * java.util.function.Predicate<T>
     *     接口定义了一个名叫test的抽象方法，它接受泛型T对象，并返回一个boolean。
     */
    @Test
    public void testPredicate() {
        Predicate<String> nonEmptyStringPredicate = (s) -> !s.isEmpty();
        List<String> listOfStrings = Arrays.asList("a","b","","c","","d");
        List<String> nonEmpty = filter(listOfStrings,nonEmptyStringPredicate);
        nonEmpty.forEach(System.out::println);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T s : list) {
            if (p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }

    /**
     * java.util.function.Consumer<T>定义了一个名叫accept的抽象方法，它接受泛型T的对象，没有返回（void）。
     */
    @Test
    public void testConsumer() {
        forEach(Arrays.asList(1,2,3,4,5),(e) -> System.out.println(e));
    }

    public static <T> void forEach(List<T> list, Consumer<T> c){
        for (T i : list) {
            c.accept(i);
        }
    }

    /**
     * java.util.function.Function<T, R>接口定义了一个叫作apply的方法，它接受一个泛型T的对象，并返回一个泛型R的对象。
     */
    public void testFunction() {
        List<Integer> l = map(Arrays.asList("lambda","in","action"),(s) -> s.length());
        l.forEach(System.out::println);
    }

    public static <T,R> List<R> map(List<T> list, Function<T,R> f) {
        List<R> result = new ArrayList<>();
        for (T s : list) {
            result.add(f.apply(s));
        }
        return result;
    }

    /**
     * java.util.function包下定义专门的函数式接口避免自动装箱操作
     */
    public void testAutoBoxing() {
        IntPredicate evenNumber = (i) -> i % 2 == 0;
        // 无装箱
        evenNumber.test(2000);

        Predicate<Integer> oldNumber = (i) -> i % 2 == 0;
        // 有装箱，将字面量2000装箱成Integer对象
        oldNumber.test(2000);
    }
}
