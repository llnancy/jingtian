package io.github.llnancy.middleware.flink.watermark;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * 无序流内置 Watermark 策略
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/24
 */
public class WatermarkOutOfOrderDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Tuple2<String, Integer>> dss = env.fromData(
                Tuple2.of("a", 1),
                Tuple2.of("a", 2),
                Tuple2.of("a", 5),
                Tuple2.of("a", 3),
                Tuple2.of("a", 10),
                Tuple2.of("a", 8),
                Tuple2.of("a", 11),
                Tuple2.of("a", 6),
                Tuple2.of("a", 13), // 该条数据的到来触发 [0, 10) 窗口的计算
                Tuple2.of("b", 2),
                Tuple2.of("b", 3)
        );

        dss.assignTimestampsAndWatermarks(
                        WatermarkStrategy.<Tuple2<String, Integer>>forBoundedOutOfOrderness(Duration.ofSeconds(3)) // 指定无序 Watermark 生成策略，最多允许 3 秒延迟
                                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple2<String, Integer>>() {
                                    @Override
                                    public long extractTimestamp(Tuple2<String, Integer> element, long recordTimestamp) {
                                        System.out.println("extractTimestamp 数据：" + element + ", recordTimestamp = " + recordTimestamp);
                                        // 从数据中提取事件时间戳，返回值的单位为毫秒。
                                        return element.f1 * 1000L;
                                    }
                                })
                )
                .keyBy(value -> value.f0)
                .window(TumblingEventTimeWindows.of(Duration.ofSeconds(10))) // 使用事件时间语义的窗口
                .process(new ProcessWindowFunction<Tuple2<String, Integer>, String, String, TimeWindow>() {

                    private static final long serialVersionUID = -8529648100224644343L;

                    @Override
                    public void process(String s, ProcessWindowFunction<Tuple2<String, Integer>, String, String, TimeWindow>.Context context, Iterable<Tuple2<String, Integer>> elements, Collector<String> out) throws Exception {
                        TimeWindow window = context.window();
                        long windowStart = window.getStart();
                        long windowEnd = window.getEnd();
                        String start = DateFormatUtils.format(windowStart, "yyyy-MM-dd hh:mm:ss");
                        String end = DateFormatUtils.format(windowEnd, "yyyy-MM-dd hh:mm:ss");
                        // elements 是窗口内数据的迭代器，可获取到窗口内的全部数据。
                        long count = elements.spliterator().estimateSize();
                        System.out.println("key = " + s + " 的窗口 windowStart[" + start + "," + end + "] 包含 " + count + " 条数据：" + elements);
                    }
                })
                .print();

        env.execute();
    }
}
