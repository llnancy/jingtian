package io.github.llnancy.middleware.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 并行度 parallelism
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/12
 */
public class Parallelism {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // IDEA 中运行也可看到 WebUI，一般用于本地测试。需要引入 flink-runtime-web 依赖。
        // StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        // 设置执行环境层次的默认并行度
        env.setParallelism(3);

        // 从 Socket 9999 端口按行读取数据
        DataStreamSource<String> dss = env.socketTextStream("localhost", 9999);

        // 处理数据
        dss.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        String[] words = value.split(" ");
                        for (String word : words) {
                            out.collect(Tuple2.of(word, 1));
                        }
                    }
                })
                .setParallelism(2) // 设置 flatMap 算子的并行度
                .keyBy(value -> value.f0)
                .sum(1)
                .print();

        env.execute();
    }
}
