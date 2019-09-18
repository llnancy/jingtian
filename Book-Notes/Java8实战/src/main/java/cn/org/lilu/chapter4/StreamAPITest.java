package cn.org.lilu.chapter4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/16
 * @Description: 第四章：引入流。书中代码测试。
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
     * 查找热量高（热量大于300）的三道菜的菜名并保存到list中
     */
    @Test
    public void test1() {
        List<String> threeHighCaloricDishName = menu.stream().
                filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(threeHighCaloricDishName);
    }

    /**
     * 流只能被消费一次
     * the stream can only be consumed once
     */
    @Test
    public void test2() {
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        // 重复消费会抛出异常
        // java.lang.IllegalStateException: stream has already been operated upon or closed
        s.forEach(System.out::println);
    }
}
