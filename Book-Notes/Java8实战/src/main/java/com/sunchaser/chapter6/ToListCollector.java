package com.sunchaser.chapter6;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author: sunchaser
 * @date: 2019/8/20
 * @description: 自定义收集器实现：将Stream流中的所有元素收集到List中。
 *
 * Collector<T,List<T>, List<T>>接口定义如下：
 * public interface Collector<T, A, R> {
 *     Supplier<A> supplier();
 *     BiConsumer<A, T> accumulator();
 *     BinaryOperator<A> combiner();
 *     Function<A, R> finisher();
 *     Set<Characteristics> characteristics();
 * }
 * 泛型T,A,R含义如下：
 * T：流中要收集的对象的泛型。
 * A：累加器的类型，累加器是在收集过程中用于累计部分结果的对象。
 * R：收集操作得到的对象类型（通常但不一定是集合）。
 *
 */
public class ToListCollector<T> implements Collector<T,List<T>, List<T>> {

    /**
     * 创建新的结果容器。返回一个供给型函数式接口。
     * @return 必须返回一个空的Supplier。
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /**
     * 将元素添加到结果容器。返回一个消费型函数式接口。
     * accumulator方法会返回执行归约操作的函数。
     * 当遍历到流中第n个元素时，这个函数执行时会有两个参数：
     * 保存归约结果的累加器（已收集了流中的前 n-1个项目），还有第n个元素本身。
     * 该函数将返回void，因为累加器是原位更新，即函数的执行改变了它的内部状态以体现遍历的元素的效果
     * @return
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /**
     * 返回一个供归约操作使用的函数，它定义了对流的各个子部分进行并行处理时，
     * 各个子部分归约所得的累加器要如何合并。
     *
     * 并行归约：使用Fork/Join框架
     * @return
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1,list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * 对结果容器应用最终转换。
     * 在遍历完流后，finisher方法必须返回在累积过程的后要调用的一个函数，
     * 以便将累加器对象转换为整个集合操作的最终结果。
     * @return
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    /**
     * 返回一个不可变的Characteristics集合，它定义了收集器的行为——尤其是关于流是否可以并行归约，
     * 以及可以使用哪些优化的提示。
     *
     * Characteristics是一个包含三个项目的枚举：
     * UNORDERED：归约结果不受流中项目的遍历和累积顺序的影响。
     * CONCURRENT：accumulator函数可以从多个线程同时调用，且该收集器可以并行归约流。
     *            如果收集器没有标为UNORDERED，那它仅在用于无序数据源时才可以并行归约。
     * IDENTITY_FINISH：这表明完成器方法返回的函数是一个恒等函数，可以跳过。
     *                  这种情况下，累加器对象将会直接用作归约过程的终结果。
     *                  这也意味着，将累加器A不加检查地转换为结果R是安全的。
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH,Characteristics.CONCURRENT));
    }
}
