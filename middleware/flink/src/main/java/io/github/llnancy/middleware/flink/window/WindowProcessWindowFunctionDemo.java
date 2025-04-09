package io.github.llnancy.middleware.flink.window;

import io.github.llnancy.middleware.flink.WordCount;
import org.apache.commons.lang3.time.DateFormatUtils;
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
 * process window function
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/20
 */
public class WindowProcessWindowFunctionDemo {

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
                /*
                 * 泛型参数：
                 * 1. 输入类型：数据流中元素的类型
                 * 2. 输出类型：聚合计算后的输出结果类型
                 * 3. 分区 key 类型：keyBy 的字段类型
                 * 4. 窗口类型
                 */
                .process(new ProcessWindowFunction<WordCount, String, String, TimeWindow>() {

                    /**
                     *
                     * @param s The key for which this window is evaluated.
                     * @param context The context in which the window is being evaluated.
                     * @param elements The elements in the window being evaluated.
                     * @param out A collector for emitting elements.
                     * @throws Exception throws
                     */
                    @Override
                    public void process(String s, ProcessWindowFunction<WordCount, String, String, TimeWindow>.Context context, Iterable<WordCount> elements, Collector<String> out) throws Exception {
                        // 上下文可以拿到 window 等信息
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
