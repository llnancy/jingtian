package io.github.llnancy.middleware.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * DataStream API Demo
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/18
 */
public class DataStreamApiWordCountDemo {

    public static void main(String[] args) throws Exception {
        // 1. 获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 手动指定为批处理模式执行
        // env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        // 2. 从集合中读取源数据
        DataStreamSource<String> dss = env.fromData(Arrays.asList("hello world", "hello java", "hello flink"));
        // 3. 切分、转换成 Tuple2。注意这里不能直接使用 lambda 表达式。
        SingleOutputStreamOperator<Tuple2<String, Integer>> operator = dss.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                String[] words = value.split(" ");
                for (String word : words) {
                    out.collect(new Tuple2<>(word, 1));
                }
            }
        });
        // 4. 按 Tuple 第 0 个元素分组。可使用 lambda 表达式简写。value -> value.f0
        KeyedStream<Tuple2<String, Integer>, String> ks = operator.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> value) throws Exception {
                return value.f0;
            }
        });
        // 5. 按 Tuple 第 1 个元素累加
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = ks.sum(1);
        // 6. 打印结果
        sum.print();
        // 7. 触发任务执行
        env.execute();

        // StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // env.fromData(Arrays.asList("hello world", "hello java", "hello flink"))
        //         // .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
        //         //     @Override
        //         //     public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
        //         //         String[] words = value.split(" ");
        //         //         for (String word : words) {
        //         //             out.collect(new Tuple2<>(word, 1));
        //         //         }
        //         //     }
        //         // })
        //         .flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
        //             String[] words = value.split(" ");
        //             for (String word : words) {
        //                 out.collect(new Tuple2<>(word, 1));
        //             }
        //         })
        //         // 要显式地告知 Flink 当前的返回值泛型类型，才能使用 lambda 表达式。
        //         .returns(Types.TUPLE(Types.STRING, Types.INT))
        //         .keyBy(value -> value.f0)
        //         .sum(1)
        //         .print();
        // env.execute();
    }
}
