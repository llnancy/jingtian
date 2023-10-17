package io.github.llnancy.jingtian.javase.java8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream api test
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/13
 */
public class StreamAPITest {

    public static void main(String[] args) throws Exception {
        // createStream();

        filter();

        distinct();

        limit();

        skip();

        map();

        flatMap();

        findFirst();

        findAny();

        allMatch();

        noneMatch();

        anyMatch();

        sorted();

        reduce();

        mapReduce();

        aggregation();

        group();

        partition();

        joining();

        parallelStream();
    }

    /**
     * 创建流
     */
    private static void createStream() throws IOException {
        // 从 Collection 获取流。Collection 接口提供了默认方法 stream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        // 从值序列获取流。Stream 接口提供了静态方法 of
        Stream<String> stream2 = Stream.of("a", "b", "c");

        // 创建一个空流
        Stream<String> empty = Stream.empty();

        // 从数组获取流。不支持基本数据类型
        String[] arr = {"a", "b", "c"};
        Stream<String> stream3 = Stream.of(arr);

        // 从文件获取流。java.nio.file.Files 中的很多静态方法都会返回流。
        Stream<String> stream4 = Files.lines(Paths.get("/Users/path"));

        // 从生成函数获取流。
        Stream<Integer> stream5 = Stream.iterate(1, i -> i + 1);
        Stream<Double> stream6 = Stream.generate(Math::random);
    }

    /**
     * 谓词筛选
     */
    private static void filter() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Java", "Python", "JavaScript", "Spring");

        // filter 接收一个 Predicate 断言型函数式接口参数作为筛选条件
        list.stream()
                .filter(el -> el.startsWith("J"))
                .forEach(System.out::println);
    }

    /**
     * 去重
     */
    private static void distinct() {
        // 根据流中元素的 hashCode 和 equals 方法实现去重
        Stream.of(11, 22, 33, 11, 33)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 截断
     */
    private static void limit() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Java", "Python", "JavaScript", "Spring");
        // 截取流中前 n 个元素
        list.stream()
                .limit(3)
                .forEach(System.out::println);
    }

    /**
     * 跳过
     */
    private static void skip() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Java", "Python", "JavaScript", "Spring");
        // 跳过流中前 n 个元素
        list.stream()
                .skip(2)
                .forEach(System.out::println);
    }

    /**
     * 映射
     */
    private static void map() {
        // map 接收一个 Function 函数型函数式接口参数，将当前流中的 T 类型数据转化为 R 类型
        Stream.of("11", "22", "33")
                .map(Integer::parseInt)
                .forEach(System.out::println);
    }

    /**
     * 扁平化映射
     */
    private static void flatMap() {
        List<String> list1 = Arrays.asList("33", "44", "55");
        List<String> list2 = Arrays.asList("66", "77", "88");
        List<List<String>> list = Arrays.asList(list1, list2);

        // flatMap 将流中的每个元素合并起来，扁平化为一个流。
        list.stream()
                .flatMap(el -> el.stream().map(Integer::parseInt))
                .forEach(System.out::println);
    }

    /**
     * 查找第一个
     */
    private static void findFirst() {
        Stream.of(6, 7, 5, 1)
                .findFirst()
                .ifPresent(System.out::println);
    }

    /**
     * 查找任意一个
     */
    private static void findAny() {
        Stream.of(6, 7, 5, 1)
                .findAny()
                .ifPresent(System.out::println);
    }

    /**
     * 全匹配
     */
    private static void allMatch() {
        // 流中元素是否全部满足条件
        boolean allMatch = Stream.of(5, 3, 6, 1)
                .allMatch(el -> el > 1);
        System.out.println(allMatch);
    }

    /**
     * 全不匹配
     */
    private static void noneMatch() {
        // 流中元素是否全部不满足条件
        boolean noneMatch = Stream.of(5, 3, 6, 1)
                .noneMatch(el -> el > 1);
        System.out.println(noneMatch);
    }

    /**
     * 任一匹配
     */
    private static void anyMatch() {
        // 流中元素是否有任意一个满足条件
        boolean anyMatch = Stream.of(5, 3, 6, 1)
                .anyMatch(el -> el > 1);
        System.out.println(anyMatch);
    }

    /**
     * 排序
     */
    private static void sorted() {
        Stream.of(11, 33, 22, 55)
                .sorted() // 按自然顺序排序
                .sorted(Comparator.comparingInt(o -> o)) // 按比较器排序
                .forEach(System.out::println);
    }

    /**
     * 归约
     */
    private static void reduce() {
        Integer reduce = Stream.of(4, 5, 3, 9)
                .reduce(0, (a, b) -> {
                    System.out.println("a = " + a + ", b = " + b);
                    return a + b;
                });
        // reduce：
        // 第一次：a 初始值为 0，取出流中第一个元素 4 赋值给 b，执行加法得到结果 4；
        // 第二次：将第一次的加法结果 4 赋值给 a，取出流中第二个元素 5 赋值给 b，执行加法得到结果 9；
        // 第三次：将第二次的加法结果 9 赋值给 a，取出流中第三个元素 3 赋值给 b，执行加法得到结果 12；
        // 第四次：将第三次的加法结果 12 赋值给 a，取出流中第三个元素 9 赋值给 b，执行加法得到结果 21；
        System.out.println(reduce);
    }

    /**
     * map-reduce
     */
    private static void mapReduce() {
        // 统计数字 2 出现的次数
        Integer count = Stream.of(1, 2, 2, 1, 3, 2)
                .map(el -> el == 2 ? 1 : 0)
                .reduce(0, Integer::sum);
        System.out.println(count);
    }

    /**
     * 聚合
     */
    private static void aggregation() {
        // 最大值
        Optional<Integer> max1 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .collect(Collectors.maxBy(Comparator.comparingInt(el -> el)));

        Optional<Integer> max2 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .max(Comparator.comparingInt(el -> el));

        // 最小值
        Optional<Integer> min1 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .collect(Collectors.minBy(Comparator.comparingInt(el -> el)));

        Optional<Integer> min2 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .min(Comparator.comparingInt(el -> el));

        // 求总和
        Integer sum1 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .collect(Collectors.summingInt(el -> el));

        Integer sum2 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .mapToInt(String::length)
                .sum();

        // 平均数
        Double average = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .collect(Collectors.averagingInt(el -> el));

        // 统计总数
        Long count1 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .collect(Collectors.counting());

        long count2 = Stream.of("Java", "Python", "JavaScript", "Spring")
                .count();

        // 一次操作获取多个聚合结果
        IntSummaryStatistics statistics = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(String::length)
                .collect(Collectors.summarizingInt(el -> el));
        int max = statistics.getMax();
        int min = statistics.getMin();
        long sum = statistics.getSum();
        double avg = statistics.getAverage();
        long count = statistics.getCount();
    }

    /**
     * 分组
     */
    private static void group() {
        // 根据首字母进行分组，首字母相同的分为同一组
        Map<String, Set<String>> group = Stream.of("Java", "Python", "JavaScript", "Spring", "Java")
                .collect(
                        Collectors.groupingBy(
                                el -> el.substring(0, 1),
                                HashMap::new,
                                Collectors.toSet()
                        )
                );
        // {P=[Python], S=[Spring], J=[Java, JavaScript]}
        System.out.println(group);
    }

    /**
     * 分区
     */
    private static void partition() {
        Map<Boolean, Set<String>> partition = Stream.of("Java", "Python", "JavaScript", "Spring", "Java")
                .collect(
                        Collectors.partitioningBy(
                                el -> el.startsWith("J"),
                                Collectors.toSet()
                        )
                );
        // {false=[Spring, Python], true=[Java, JavaScript]}
        System.out.println(partition);
    }

    /**
     * 拼接
     */
    private static void joining() {
        String joining = Stream.of("Java", "Python", "JavaScript", "Spring")
                .map(el -> "'" + el + "'")
                .collect(Collectors.joining(", "));
        // 'Java', 'Python', 'JavaScript', 'Spring'
        System.out.println(joining);
    }

    /**
     * 并行流
     */
    private static void parallelStream() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Java", "Python", "JavaScript", "Spring");

        // 直接获取并行流
        long count1 = list.parallelStream()
                .filter(el -> {
                    System.out.println(Thread.currentThread() + ", el = " + el);
                    return true;
                })
                .count();

        // 将串行流转为并行流
        long count2 = list.stream()
                .parallel()
                .filter(el -> {
                    System.out.println(Thread.currentThread() + ", el = " + el);
                    return true;
                })
                .count();
    }
}
