package io.github.llnancy.middleware.flink.window;

import io.github.llnancy.middleware.flink.WordCount;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;

/**
 * window assigner demo
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/20
 */
public class WindowAssignerDemo {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WordCount> dss = env.fromData(WordCount.list());

        dss.keyBy(new KeySelector<WordCount, String>() {
                    @Override
                    public String getKey(WordCount value) throws Exception {
                        return value.word;
                    }
                })
                // 时间窗口
                // .window(TumblingProcessingTimeWindows.of(Duration.ofSeconds(5))); // 窗口大小为 5 秒的滚动窗口
                // .window(SlidingProcessingTimeWindows.of(Duration.ofSeconds(10), Duration.ofSeconds(5))); // 窗口大小为 10 秒，滑动步长为 5 秒的滑动窗口
                // .window(ProcessingTimeSessionWindows.withGap(Duration.ofSeconds(5))); // 时间间隔为 5 秒的 session 会话窗口
                // 计数窗口
                // .countWindow(5); // 窗口大小为 5 的滚动窗口。每 5 个元素划分至一个窗口。
                // .countWindow(5, 2); // 窗口大小为 5，滑动步长为 2 的滑动窗口。
                // 全局窗口
                .window(GlobalWindows.create()); // 全局窗口。需要自定义触发器 trigger 才能触发窗口终点进行计算。
    }
}
