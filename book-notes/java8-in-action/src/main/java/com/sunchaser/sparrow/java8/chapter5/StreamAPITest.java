package com.sunchaser.sparrow.java8.chapter5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author sunchaser
 * @date 2019/8/16
 * @description 第五章：使用流。书中代码测试。
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StreamAPITest {

    /**
     * 初始化菜品集合
     */
    List<Dish> menu = Arrays.asList(
            new Dish("pork",false,800,Dish.Type.MEAT),
            new Dish("beef",false,700,Dish.Type.MEAT),
            new Dish("chicken",false,400,Dish.Type.MEAT),
            new Dish("french fries",true,530,Dish.Type.OTHER),
            new Dish("rice",true,350,Dish.Type.OTHER),
            new Dish("season fruit",true,120,Dish.Type.OTHER),
            new Dish("pizza",true,550,Dish.Type.OTHER),
            new Dish("prawns",false,300,Dish.Type.FISH),
            new Dish("salmon",false,450,Dish.Type.FISH)
    );

    /**
     * filter()：用谓词筛选
     * 筛选出所有素菜
     */
    @Test
    public void testFilter() {
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());
        vegetarianMenu.forEach(System.out::println);
    }

    /**
     * distinct()：筛选各异的元素
     */
    @Test
    public void testDistinct() {
        List<Integer> nums = Arrays.asList(1,2,1,2,3,4,3,2,1);
        nums.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * limit()
     * 筛选出热量超过300卡路里的前三道菜
     */
    @Test
    public void testLimit() {
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());
        dishes.forEach(System.out::println);
    }

    /**
     * skip()
     * 跳过超过300卡路里的头两道菜，并返回剩下的。
     */
    @Test
    public void testSkip() {
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());
        dishes.forEach(System.out::println);
    }

    /**
     * 测验5.1：
     * 筛选前2个荤菜
     */
    @Test
    public void testExercise5_1() {
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
        dishes.forEach(System.out::println);
    }

    /**
     * map()
     * 提取所有菜品的名字的长度
     */
    @Test
    public void testMap() {
        List<Integer> menuNames = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        menuNames.forEach(System.out::println);
    }

    /**
     * flatMap()
     * 测验5.2
     * 1、给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？例如，给定[1, 2, 3, 4, 5]，应该返回[1, 4, 9, 16, 25]。
     */
    @Test
    public void testExercise5_2_1() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        List<Integer> result = nums.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        result.forEach(System.out::println);
    }

    /**
     * 2、给定两个数字列表，如何返回所有的数对呢？
     * 例如，给定列表[1, 2, 3]和列表[3, 4]，
     * 应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。
     * 为简单起见，你可以用有两个元素的数组来代表数对
     */
    @Test
    public void testExercise5_2_2() {
        List<Integer> nums1 = Arrays.asList(1,2,3);
        List<Integer> nums2 = Arrays.asList(3,4);
        List<int[]> result = nums1.stream()
                .flatMap(i -> nums2.stream()
                        .map(j -> new int[] {i, j}))
                .collect(Collectors.toList());
        result.forEach(e -> Arrays.stream(e).forEach(System.out::println));
    }

    /**
     * 扩展前一个例子，只返回总和能被3整除的数对呢？例如(2, 4)和(3, 3)是可以的。
     */
    @Test
    public void testExercise5_2_3() {
        List<Integer> nums1 = Arrays.asList(1,2,3);
        List<Integer> nums2 = Arrays.asList(3,4);
        List<int[]> result = nums1.stream()
                .flatMap(i -> nums2.stream()
                        .filter(e -> (i + e) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());
        result.forEach(e -> Arrays.stream(e).forEach(System.out::println));
    }

    /**
     * anyMatch()：流中是否有一个元素能匹配给定的谓词
     * 看菜单里面是否有素食。
     */
    @Test
    public void testAnyMatch() {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("yes");
        }
    }

    /**
     * allMatch()：流中的元素是否都能匹配给定的谓词
     * 看菜品是否有利健康（即所有菜的热量都低于1000卡路里）。
     */
    @Test
    public void testAllMatch() {
        if (menu.stream().allMatch(d -> d.getCalories() < 1000)) {
            System.out.println("yes");
        }
    }

    /**
     * noneMatch()：流中没有任何元素与给定的谓词匹配。
     * 重写testAllMatch()方法
     */
    @Test
    public void testNoneMatch() {
        if (menu.stream().noneMatch(d -> d.getCalories() > 1000)) {
            System.out.println("yes");
        }
    }

    /**
     * findAny()：方法将返回当前流中的任意元素。
     * 找一道素菜
     */
    @Test
    public void testFindAny() {
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        System.out.println(dish.get());

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));
    }

    /**
     * findFirst()：取流中的第一个元素。
     * 给定一个数字列表，找出第一个平方 能被3整除的数。
     */
    @Test
    public void testFindFirst() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        Optional<Integer> first = nums.stream()
                .map(e -> e * e)
                .filter(e -> e % 3 == 0)
                .findFirst();
        first.ifPresent(System.out::println);
    }

    /**
     * reduce()：归约操作
     *
     * 包含两个重载的方法
     * 1、T reduce(T identity, BinaryOperator<T> accumulator);
     * 第一个参数：初始值identity
     * 第二个参数：BinaryOperator<T>将2个元素结合起来产生一个新值
     *
     * 2、Optional<T> reduce(BinaryOperator<T> accumulator);
     *
     *
     */
    @Test
    public void testReduce() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6);
        // 将流中所有元素求和
        // 第一个参数：初始值0
        // 第二个参数：lambda (x,y) -> x + y
        Integer sum = nums.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        // 将流中所有元素相乘
        Integer product = nums.stream().reduce(1, (x, y) -> x * y);
        System.out.println(product);
        // 使用方法引用。Integer类中提供了一个sum()方法用于2个数求和
        sum = nums.stream().reduce(0,Integer::sum);
        System.out.println(sum);

        // 重载的reduce()方法，不接收初始值参数，返回的是一个Optional对象。
        // 为什么返回Optional对象？如果流中没有任何元素，此时也无初始值，reduce操作无法返回任何结果。
        Optional<Integer> optionalSum = nums.stream().reduce((x, y) -> x + y);
    }

    /**
     * 使用归约计算流中元素的最大值和最小值
     */
    @Test
    public void testReduceMaxMin() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6);
        Optional<Integer> max = nums.stream().reduce(Integer::max);
        Optional<Integer> min = nums.stream().reduce(Integer::min);
        max.ifPresent(System.out::println);
        min.ifPresent(System.out::println);
    }

    /**
     * map和reduce的连接通常称为map-reduce模式，因Google用它来进行网络搜索而出名，因为它很容易并行化。
     * 用map和reduce方法计算流中有多少个菜
     */
    @Test
    public void testReduceCount() {
        Integer count = menu.stream()
                .map(d -> 1)
                .reduce(0, Integer::sum);
        System.out.println(count);
        // 使用内置count()方法计算流中元素个数
        long lCount = menu.stream().count();
        System.out.println(lCount);
    }

    /**
     * 原始流特化
     * Java 8引入了三个原始类型特化流接口：IntStream、DoubleStream和LongStream，
     * 分别将流中的元素特化为int、long和double，从而避免了暗含的装箱成本。
     *
     * 一旦有了数值流，你可能会想把它转换回非特化流。
     * 调用特化流接口的装箱方法boxed()将原始类型装箱成对应的包装类型，从而转回对象流。
     *
     * 对于原始类型特化流接口中的max，min，average等方法的返回值。
     * 如果流是空的，这些方法的返回值为空，但不能默认为0。因为可能真实计算的结果恰好为0。
     * 可以使用Optional类来解决返回值为空的情况。但Optional<T>只能接收包装类型。传递原始类型会触发自动装箱操作，产生损耗。
     *
     * Java 8同样引入了Optional原始类型特化版本：OptionalInt、OptionalDouble和OptionalLong
     * 用这些Optional类来解决传递原始类型时自动装箱的问题。
     */
    @Test
    public void testSpecialStream() {
        // mapToInt()方法返回一个IntStream对象，而不是Stream<Integer>对象。
        IntStream intStream = menu.stream()
                .mapToInt(Dish::getCalories);
        // IntStream类中定义了sum()等方法（max，min，average）方便对int值的操作。
        // 如果流是空的，sum()默认返回0
        int sum = intStream.sum(); // 流已被消费
        System.out.println(sum);

        // 转回对象流，调用boxed()方法进行装箱
        Stream<Integer> stream = menu.stream()
                .mapToInt(Dish::getCalories)
                .boxed();

        // 如果流是空的，max，min，average等方法返回值也是0吗？
        // 如果max，min，average等方法真正计算的结果也为0呢？这时候如果默认返回0就会出现问题了。
        OptionalInt max = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        // 如果流为空，则最大值max为空，使用OptionalInt接收后。可以利用OptionalInt对象显式定义一个默认值。
        int maxInt = max.orElse(1);
        System.out.println(maxInt);
    }

    /**
     * 和数字打交道时，有一个常用的东西就是数值范围。比如，假设你想要生成1和100之间的所有数字。
     * Java 8引入了两个可以用于IntStream和LongStream的静态方法，帮助生成这种范围： range和rangeClosed。
     * IntStream range(int startInclusive, int endExclusive);
     * IntStream rangeClosed(int startInclusive, int endInclusive);
     * 这两个方法都是第一个参数接受起始值，第二个参数接受结束值。但range是不包含结束值的，而rangeClosed则包含结束值。
     */
    @Test
    public void testNumRange() {
        // 计算1-100范围内偶数的个数
        // IntStream range(int startInclusive, int endExclusive)生成的范围为：[startInclusive,endExclusive)
        long countRange = IntStream.range(1, 100)
                .filter(e -> e % 2 == 0)
                .count();
        // IntStream rangeClosed(int startInclusive, int endInclusive)生成的范围为：[startInclusive,endExclusive]
        long countRangeClosed = IntStream.rangeClosed(1, 100)
                .filter(e -> e % 2 == 0)
                .count();
        System.out.println(countRange);
        System.out.println(countRangeClosed);
    }

    /**
     * 数值流应用：生成勾股数（a^2 + b^2 = c^2）
     */
    @Test
    public void testGeneratePythagoreanTriples() {
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100) // 生成[1,100]范围内的int值
                .boxed() // 装箱成Integer，生成Stream<Integer>流
                .flatMap(a -> IntStream.rangeClosed(a, 100) // 生成[a,100]范围内的int值。从a开始生成避免重复。
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0) // 过滤出满足条件的b。a^2+b^2结果开平方为整数才满足条件
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}) // 将IntStream流映射成三元数组。
                );
        // 遍历输出三元数组
        pythagoreanTriples.forEach(i -> System.out.println(i[0] + "," + i[1] + "," + i[2]));

        // 先生成[1,100]内所有的三元数组，再过滤出满足条件的。
        Stream<int[]> stream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(e -> e[2] % 1 == 0)
                ).map(e -> new int[] {(int) e[0], (int) e[1], (int) e[2]}); // double[]流转int[]流
        stream.forEach(i -> System.out.println(i[0] + "," + i[1] + "," + i[2]));
    }

    /**
     * 使用静态方法Stream.of，通过显式值创建一个流。它可以接受任意数量的参数。
     *
     * 使用静态方法Stream.empty，创建一个空流。
     */
    @Test
    public void testGenerateStreamByStreamAPI() {
        // 使用静态方法Stream.of，通过显式值创建一个流。
        Stream<String> stringStream = Stream.of("Java 8", "Lambda", "Stream");
        stringStream.map(String::toUpperCase).forEach(System.out::println);

        // 使用Stream.empty()创建一个空流。
        Stream<Object> emptyStream = Stream.empty();
        emptyStream.forEach(System.out::println);
    }

    /**
     * 使用静态方法Arrays.stream从数组创建一个流。它接受一个数组作为参数。
     */
    @Test
    public void testGenerateStreamByArrays() {
        // 使用静态方法Arrays.stream从数组创建一个流。
        int sum = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6}).sum();
        System.out.println(sum);
    }

    /**
     * Java中用于处理文件等I/O操作的NIO API（非阻塞 I/O）已更新，以便利用Stream API。
     * java.nio.file.Files中的很多静态方法都会返回一个流。
     *
     * Files.lines，它会返回一个由指定文件中的各行构成的字符串流
     */
    @Test
    public void testGenerateStreamByFile() {
        // Files.lines方法第一个参数是一个Path对象，文件的绝对路径。
        try(Stream<String> lines = Files.lines(Paths.get("H:\\projects\\IdeaProjects\\gold-road-to-java\\Book-Notes\\Java 8实战\\src\\main\\java\\cn\\org\\lilu\\chapter5\\data.txt"), Charset.defaultCharset())) {
            long count = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(count);
        } catch (IOException e) {
            // 读取文件异常则处理异常
            e.printStackTrace();
        }
    }

    /**
     * 无限流：
     * Stream API提供了两个静态方法来从函数生成流：Stream.iterate和Stream.generator。
     * 这两个操作可以创建所谓的无限流：不像从固定集合创建的流那样有固定大小的流。
     * 由iterate和generate产生的流会用给定的函数按需创建值，因此可以无穷无尽地计算下去！
     * 一般来说，应该使用limit(n)来对这种流加以限制，以避免打印无穷多个值
     *
     * Stream<T> iterate(final T seed, final UnaryOperator<T> f)
     * 第一个参数seed，种子值（初始值）。
     * 第二个参数UnaryOperator<T> f，一个Function类型函数式接口，依次应用到每个新值上。
     */
    @Test
    public void testGenerateStreamByIterateFunction() {
        Stream.iterate(0,n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
    }

    /**
     * 斐波纳契数列是著名的经典编程练习。
     * 下面这个数列就是斐波纳契数列的一部分：0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55…
     * 数列中开始的两个数字是0和1，后续的每个数字都是前两个数字之和。
     * 斐波纳契元组序列与此类似，是数列中数字和其后续数字组成的元组构成的序列：
     * (0, 1), (1, 1), (1, 2), (2, 3), (3, 5), (5, 8), (8, 13), (13, 21) …
     *
     * 用iterate方法生成斐波纳契元组序列中的前20个元素。
     */
    @Test
    public void testGenerateFibonacciByIterate() {
        Stream.iterate(new int[] {0,1},n -> new int[] {n[1],n[0] + n[1]})
                .limit(20)
                .forEach(e -> System.out.println(e[0] + "," + e[1]));

        // 正常打印Fibonacci数列前20项
        Stream.iterate(new int[] {0,1},n -> new int[] {n[1],n[0] + n[1]})
                .limit(20)
                .map(e -> e[0]) // 提取每个元组序列的第一个元素
                .forEach(System.out::println);
    }

    /**
     * Stream<T> generator(Supplier<T> s)
     * 接收一个Supplier供给型函数式接口
     */
    @Test
    public void testGenerateStreamByGenerateFunction() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    /**
     * 使用generate方法打印fibonacci前20项。
     *
     * 与iterate方法相比：
     * 使用iterate方法是纯粹不变的：它没有修改现有状态。但每次迭代会产生新的int[]元组。
     * generate方法在调用过程中会改变中间对象状态。
     *
     * 应该始终采用不变的方法，以便并行处理流，并保持结果正确。
     */
    @Test
    public void testGenerateFibonacciByGenerate() {
        // 使用IntSupplier供给型函数式接口避免自动装箱
        IntSupplier fibonacci = new IntSupplier() {
            // 定义第一个数（前一个数）
            private int previous = 0;
            // 定义第二个数（当前的数）
            private int current = 1;

            /**
             * 调用过程中会改变私有成员变量的状态。
             * @return
             */
            @Override
            public int getAsInt() {
                int oldPrevious = previous;
                int nextValue = previous + current;
                previous = current;
                current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fibonacci).limit(20).forEach(System.out::println);
    }
}
