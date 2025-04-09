package io.github.llnancy.middleware.flink.transformation;

import io.github.llnancy.middleware.flink.WordCount;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * keyBy transformation
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/23
 */
public class KeyByTransformation {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Tuple2<WordCount, Integer>> dss = env.fromData(
                Tuple2.of(new WordCount("Hello", 1), 1),
                Tuple2.of(new WordCount("World", 2), 2),
                Tuple2.of(new WordCount("Hello", 3), 3),
                Tuple2.of(new WordCount("Flink", 4), 4)
        );
        // 用 WordCount 类（POJO 类型）作为 key，WordCount 类必须重写 hashCode 方法
        KeyedStream<Tuple2<WordCount, Integer>, WordCount> ks = dss.keyBy(new KeySelector<Tuple2<WordCount, Integer>, WordCount>() {
            @Override
            public WordCount getKey(Tuple2<WordCount, Integer> value) throws Exception {
                return value.f0;
            }
        });
        SingleOutputStreamOperator<Tuple2<WordCount, Integer>> sum = ks.sum(1);

        sum.print();
        env.execute();
    }
}
