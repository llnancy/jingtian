package io.github.llnancy.middleware.flink.window;

import io.github.llnancy.middleware.flink.WordCount;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * aggregate + process window
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/20
 */
public class WindowAggregateAndProcessFunctionDemo {

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
                .window(TumblingProcessingTimeWindows.of(Duration.ofSeconds(10)))
                .aggregate(
                        new AggregateFunction<WordCount, Integer, String>() {

                            @Override
                            public Integer createAccumulator() {
                                System.out.println("创建累加器 createAccumulator");
                                return 0;
                            }

                            @Override
                            public Integer add(WordCount value, Integer accumulator) {
                                System.out.println("调用 add 方法，当前到来的元素为 " + value);
                                return value.count + accumulator;
                            }

                            @Override
                            public String getResult(Integer accumulator) {
                                System.out.println("调用 getResult 方法获取累加器的聚合结果");
                                return accumulator.toString();
                            }

                            @Override
                            public Integer merge(Integer a, Integer b) {
                                System.out.println("调用 merge 方法");
                                return 0;
                            }
                        },
                        new ProcessWindowFunction<String, String, String, TimeWindow>() {

                            @Override
                            public void process(String s, ProcessWindowFunction<String, String, String, TimeWindow>.Context context, Iterable<String> elements, Collector<String> out) throws Exception {
                                TimeWindow window = context.window();
                                long windowStart = window.getStart();
                                long windowEnd = window.getEnd();
                                String start = DateFormatUtils.format(windowStart, "yyyy-MM-dd hh:mm:ss");
                                String end = DateFormatUtils.format(windowEnd, "yyyy-MM-dd hh:mm:ss");
                                // elements 是 AggregateFunction 增量聚合的输出结果，仅有一条数据
                                long count = elements.spliterator().estimateSize();
                                System.out.println("key = " + s + " 的窗口 windowStart[" + start + "," + end + "] 包含 " + count + " 条数据：" + elements);
                            }
                        }
                )
                .print();
        env.execute();
    }
}
