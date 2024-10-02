package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * flatMap transformation
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/22
 */
public class FlatMapTransformation {

    public static void main(String[] args) throws Exception {
        // 将句子拆分为单词
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromData("Hello World", "Hello Apache Flink", "Hello FlatMap")
                .flatMap(new FlatMapFunction<String, String>() {
                    @Override
                    public void flatMap(String value, Collector<String> out) throws Exception {
                        for (String word : value.split(" ")) {
                            out.collect(word);
                        }
                    }
                })
                .print();
        env.execute();
    }
}
