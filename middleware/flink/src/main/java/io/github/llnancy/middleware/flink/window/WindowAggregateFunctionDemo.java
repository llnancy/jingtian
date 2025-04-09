package io.github.llnancy.middleware.flink.window;

import io.github.llnancy.middleware.flink.WordCount;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

import java.time.Duration;

/**
 * window aggregate function demo
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/20
 */
public class WindowAggregateFunctionDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dss = env.socketTextStream("localhost", 9999);
        dss.map(new MapFunction<String, WordCount>() {
                    @Override
                    public WordCount map(String value) throws Exception {
                        return new WordCount(value, 1);
                    }
                })
                .keyBy(new KeySelector<WordCount, String>() {
                    @Override
                    public String getKey(WordCount value) throws Exception {
                        return value.word.split(" ")[0];
                    }
                })
                // 滚动窗口
                .window(TumblingProcessingTimeWindows.of(Duration.ofSeconds(10)))
                // 聚合
                .aggregate(new AggregateFunction<WordCount, Integer, String>() {

                    private static final long serialVersionUID = -847962011875337781L;

                    /**
                     * 创建累加器。每个聚合任务只会调用一次
                     *
                     * @return 累加器的初始状态
                     */
                    @Override
                    public Integer createAccumulator() {
                        System.out.println("创建累加器 createAccumulator");
                        return 0;
                    }

                    /**
                     * 将当前到来的元素添加至累加器
                     *
                     * @param value The value to add
                     * @param accumulator The accumulator to add the value to
                     * @return 当前累加的状态
                     */
                    @Override
                    public Integer add(WordCount value, Integer accumulator) {
                        System.out.println("调用 add 方法，当前到来的元素为 " + value);
                        return value.count + accumulator;
                    }

                    /**
                     * 获取累加器的聚合结果
                     *
                     * @param accumulator The accumulator of the aggregation
                     * @return 聚合结果
                     */
                    @Override
                    public String getResult(Integer accumulator) {
                        System.out.println("调用 getResult 方法获取累加器的聚合结果");
                        return accumulator.toString();
                    }

                    /**
                     * 合并两个累加器，将合并后的状态作为一个累加器返回
                     *
                     * @param a An accumulator to merge
                     * @param b Another accumulator to merge
                     * @return 新的累加器
                     */
                    @Override
                    public Integer merge(Integer a, Integer b) {
                        System.out.println("调用 merge 方法");
                        return 0;
                    }
                })
                .print();
        env.execute();
    }
}
