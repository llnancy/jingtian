package cn.org.lilu.chapter6;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/20
 * @Description: 第六章：用流收集数据。书中代码测试。
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
     * 数一数菜单中有多少种菜
     */
    @Test
    public void testCountMenu() {
        // 利用counting工厂返回的收集器
        Long count1 = menu.stream().collect(Collectors.counting());
        System.out.println(count1);
        // 利用Stream接口提供的count方法
        long count2 = menu.stream().count();
        System.out.println(count2);
    }

    /**
     * 查找流中的最大值和最小值。
     *
     * 找出菜单中热量最高的菜。
     */
    @Test
    public void testMaxMinInStream() {
        Optional<Dish> dish = menu.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        System.out.println(dish);
    }

    /**
     * 汇总：
     *
     * Collectors类专门为汇总提供了一个工厂方法：Collectors.summingInt。
     * 它可接受一 个把对象映射为求和所需int的函数，并返回一个收集器；
     * 该收集器在传递给普通的collect方法后即执行我们需要的汇总操作。
     *
     * 类似的还有Collectors.summingDouble方法和Collectors.summingLong方法，求和为double和long。
     *
     * 汇总不仅仅只有求和。
     * 平均数：
     * Collectors.averagingInt
     * Collectors.averagingDouble
     * Collectors.averagingLong
     *
     * 一次操作取得多个汇总结果
     * Collectors.summarizingInt
     * Collectors.summarizingDouble
     * Collectors.summarizingLong
     * 可以通过对用getter方法取得汇总结果。
     */
    @Test
    public void testSumming() {
        // 求菜单列表总热量
        Integer caloriesSum = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(caloriesSum);
        // 求菜单列表平均值
        Double caloriesAverage = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(caloriesAverage);
        // 一次操作得到多个汇总结果
        IntSummaryStatistics collect = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(collect);
    }

    /**
     * joining工厂方法返回的收集器会把对流中每一个对象应用toString方法得到的所有字符串连接成一个字符串。
     *
     * joining方法有3个重载的方法：
     * // 内部使用StringBuilder拼接
     * joining()
     * // 一个参数，表示每个字符串连接时的分隔符
     * joining(CharSequence delimiter)
     * // 三个参数。第一个参数表示分隔符，第二个参数表示字符串前缀，第三个参数表示字符串后缀。
     * joining(CharSequence delimiter,CharSequence prefix,CharSequence suffix)
     */
    @Test
    public void testJoining() {
        String joinName1 = menu.stream().map(Dish::getName).collect(Collectors.joining());
        String joinName2 = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
        String joinName3 = menu.stream().map(Dish::getName).collect(Collectors.joining(",","[","]"));
        System.out.println(joinName1);
        System.out.println(joinName2);
        System.out.println(joinName3);
    }

    /**
     * 分组：groupingBy
     *
     * 简单属性访问分组条件：按菜的Type类型分组。
     *
     * 复杂分组条件：
     * 把热量不到400卡路里的菜划分为“低热量”（diet），
     * 热量400到700 卡路里的菜划为“普通”（normal），
     * 高于700卡路里的划为“高热量”（fat）。
     */
    @Test
    public void testGroupingBy() {
        Map<Dish.Type, List<Dish>> groupByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(groupByType);
        Map<String, List<Dish>> groupByCalories = menu.stream().collect(Collectors.groupingBy(dish -> {
            if (dish.getCalories() < 400) {
                return "diet";
            } else if (dish.getCalories() <= 700) {
                return "normal";
            } else {
                return "fat";
            }
        }));
        System.out.println(groupByCalories);
    }

    /**
     * 多级分组
     *
     * 根据菜的类型和热量进行二级分组
     *
     * 可以把第二个groupingBy收集器传递给外层收集器来实现多级分组。
     * 但进一步说，传递给第一个groupingBy的第二个收集器可以是任何类型，而不一定是另一个groupingBy。
     *
     * 计算每个类型的菜有多少个。
     *
     * groupingBy方法有多个重载：
     * // 只有一个参数，该参数是一个Function函数型接口。内部调用的是有两个参数的groupingBy方法，第二个参数传递的是Collectors.toList()方法。
     * groupingBy(Function<? super T, ? extends K> classifier)
     * // 有两个参数，第一个参数是一个Function函数型接口，第二个参数是一个收集器对象。内部调用的是三个参数的groupingBy方法。
     * groupingBy(Function<? super T, ? extends K> classifier,Collector<? super T, A, D> downstream)
     * // 有三个参数，groupingBy分组具体实现。
     * groupingBy(Function<? super T, ? extends K> classifier,Supplier<M> mapFactory,Collector<? super T, A, D> downstream)
     *
     * 收集器返回的结果可能是Optional包装后的对象，对于多级分组来说，第二个收集器对象参数返回的Optional对象可能没什么用。
     * 第一层groupingBy已经把为空的情况给排除掉了。
     * Collectors收集器提供了collectingAndThen方法将收集器进行转换
     *
     * // 第一个参数：需要转换的收集器，第二个参数：转换函数。返回转换后的另一个收集器
     * Collectors.collectingAndThen(Collector<T,A,R> downstream,Function<R,RR> finisher)
     *
     * groupingBy联合其它收集器使用：
     * 经常使用的是mapping方法。它可以让接受特定类型元素的收集器适应不同类型的对象。
     *
     * // 第一个参数是一个函数型接口，对流中的元素做映射；第二个参数是一个收集器，将映射后的元素收集起来。
     * Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper,Collector<? super U, A, R> downstream)
     */
    @Test
    public void testMultiGroupingBy() {
        // 根据菜的类型和热量进行二级分组
        Map<Dish.Type, Map<String, List<Dish>>> multiGroupingByTypeAndCalories = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.groupingBy(dish -> {
                    if (dish.getCalories() < 400) {
                        return "diet";
                    } else if (dish.getCalories() <= 700) {
                        return "normal";
                    } else {
                        return "fat";
                    }
                })));
        System.out.println(multiGroupingByTypeAndCalories);

        // 计算每个类型的菜有多少个
        Map<Dish.Type, Long> multiGroupingByTypeAndCount = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println(multiGroupingByTypeAndCount);

        // 计算每个类型中热量最高的菜
        Map<Dish.Type, Optional<Dish>> multiGroupingByTypeAndMaxCalories = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println(multiGroupingByTypeAndMaxCalories);
        // 将第二个收集器参数返回的Optional结果转换成另一种类型
        Map<Dish.Type, Dish> multiGroupingByCollectingAndThen = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
        System.out.println(multiGroupingByCollectingAndThen);

        /**
         * groupingBy联合使用其它收集器的例子
         */
        // 与summingInt收集器联合使用。计算每个分类下的菜的热量和。
        Map<Dish.Type, Integer> groupingByWithSummingInt = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.summingInt(Dish::getCalories)));
        System.out.println(groupingByWithSummingInt);
        // 与mapping收集器联合使用。查看每个分类下的菜有哪些热量等级。
        Map<Dish.Type, Set<String>> groupingByWithMapping = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.mapping(dish -> {
                    if (dish.getCalories() < 400) {
                        return "diet";
                    } else if (dish.getCalories() <= 700) {
                        return "normal";
                    } else {
                        return "fat";
                    }
                }, Collectors.toSet())));
        System.out.println(groupingByWithMapping);
    }

    /**
     * 分区
     *
     * 分区是分组的特殊情况：由一个谓词（返回一个布尔值的函数）作为分类函数，它称分区函数。
     * 分区函数返回一个布尔值，这意味着得到的分组Map的键类型是Boolean，于是它多可以分为两组——true是一组，false是一组。
     *
     * 分区也可用相同的谓词使用filter筛选来实现。
     *
     * 分区的好处：
     * 保留了分区函数返回true或false的两套流元素列表。
     * 使用filter筛选需要操作两次，一次利用谓词，一次利用谓词的非。
     *
     * partitioningBy收集器有两个重载的方法:
     * // 只有一个参数：断言型接口。内部调用了两个参数的重载方法，第二个参数传递的是一个Collectors.toList()收集器。
     * partitioningBy(Predicate<? super T> predicate)
     * // 第一个参数：断言型接口；第二个参数：收集器。
     * partitioningBy(Predicate<? super T> predicate,Collector<? super T, A, D> downstream)
     */
    @Test
    public void testPartitioningBy() {
        // 将素食和非素食分开
        Map<Boolean, List<Dish>> partitioningByIsVegetarian = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(partitioningByIsVegetarian);
        // 取出true的部分
        System.out.println(partitioningByIsVegetarian.get(true));

        // 使用filter实现分区
        List<Dish> vegetarianDishes = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(vegetarianDishes);

        // 将素食按类型分组
        Map<Boolean, Map<Dish.Type, List<Dish>>> partitioningByIsVegetarianAndType = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        System.out.println(partitioningByIsVegetarianAndType);

        // 找出素食和非素食中热量最高的菜
        Map<Boolean, Dish> partitioningByIsVegetarianAndCalories = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
        System.out.println(partitioningByIsVegetarianAndCalories);
    }
}
