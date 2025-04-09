package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Aggregation transformation
 * contains sum/min/max/minBy/maxBy
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/24
 */
public class AggregationTransformation {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Tuple3<Integer, Integer, Integer>> dss = env.fromData(
                Tuple3.of(0, 1, 0),
                Tuple3.of(0, 2, 1),
                Tuple3.of(0, 3, 2),
                Tuple3.of(1, 1, 3),
                Tuple3.of(1, 2, 4),
                Tuple3.of(1, 3, 5)
        );

        // 按 Tuple3 第 0 个位置进行分区
        KeyedStream<Tuple3<Integer, Integer, Integer>, Integer> ks = dss.keyBy(new KeySelector<Tuple3<Integer, Integer, Integer>, Integer>() {
            @Override
            public Integer getKey(Tuple3<Integer, Integer, Integer> value) throws Exception {
                return value.f0;
            }
        });

        // sum 聚合（按索引位置指定字段），适用于 TUPLE 类型，POJO 不行。
        // SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> sumByPosition = ks.sum(2);
        // sumByPosition.print();

        // sum 聚合（按字段名称指定字段。Tuple3 类中的字段名分别为 f0, f1, f2）
        // SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> sumByField = ks.sum("f2");
        // sumByField.print();

        // min 聚合
        // SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> min = ks.min(2);
        // min.print();

        // minBy 聚合
        // SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> minBy = ks.minBy(2);
        // minBy.print();

        // max 聚合
        // SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> max = ks.max(2);
        // max.print();

        // maxBy 聚合
        SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> maxBy = ks.maxBy(2);
        maxBy.print();

        env.execute();
    }
}
