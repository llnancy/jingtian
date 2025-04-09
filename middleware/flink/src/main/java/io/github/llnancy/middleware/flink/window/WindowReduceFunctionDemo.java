package io.github.llnancy.middleware.flink.window;

import io.github.llnancy.middleware.flink.WordCount;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

import java.time.Duration;

/**
 * window reduce function demo
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/20
 */
public class WindowReduceFunctionDemo {

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
                // 归约
                .reduce(new ReduceFunction<WordCount>() {
                    @Override
                    public WordCount reduce(WordCount value1, WordCount value2) throws Exception {
                        System.out.println("reduce 之前的结果 = value1=" + value1.word + ", 本次调用 reduce 到来的数据 value2=" + value2.word);
                        return new WordCount(value1.word + value2.word, 1);
                    }
                })
                .print();
        env.execute();
    }
}
