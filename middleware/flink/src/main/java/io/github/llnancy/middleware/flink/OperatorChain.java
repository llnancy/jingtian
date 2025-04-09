package io.github.llnancy.middleware.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * 算子链 operator chain
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/13
 */
public class OperatorChain {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        // 对整个作业禁用算子链
        env.disableOperatorChaining();

        DataStreamSource<String> dss = env.fromData(Arrays.asList("hello world", "hello java", "hello flink"));

        dss.filter(new FilterFunction<String>() {
                    @Override
                    public boolean filter(String value) throws Exception {
                        return !value.equals("hello world");
                    }
                })
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        String[] words = value.split(" ");
                        for (String word : words) {
                            out.collect(Tuple2.of(word, 1));
                        }
                    }
                })
                .startNewChain() // 从当前 flatMap 算子开始创建一个新链
                .map(new MapFunction<Tuple2<String, Integer>, Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> map(Tuple2<String, Integer> value) throws Exception {
                        return Tuple2.of("map: " + value.f0, value.f1);
                    }
                })
                .keyBy(value -> value.f0)
                .sum(1)
                .disableChaining() // 禁止和 sum 算子链接
                .print();
    }
}
