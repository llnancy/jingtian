package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * reduce transformation
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/24
 */
public class ReduceTransformation {

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

        KeyedStream<Tuple3<Integer, Integer, Integer>, Integer> ks = dss.keyBy(new KeySelector<Tuple3<Integer, Integer, Integer>, Integer>() {
            @Override
            public Integer getKey(Tuple3<Integer, Integer, Integer> value) throws Exception {
                return value.f0;
            }
        });
        // ReduceFunction#reduce 中的 value1 是已经聚合好的数据，value2 是新到来的数据
        SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> reduce = ks.reduce(new ReduceFunction<Tuple3<Integer, Integer, Integer>>() {
            @Override
            public Tuple3<Integer, Integer, Integer> reduce(Tuple3<Integer, Integer, Integer> value1, Tuple3<Integer, Integer, Integer> value2) throws Exception {
                System.out.println("start reduce..." + " value1:" + value1 + " value2:" + value2);

                // 实现 max 聚合的功能。取最大值，其它字段用第一条数据的值。
                // int max = Math.max(value1.f2, value2.f2);
                // value1.setField(max, 2);
                // return value1;

                // 实现 maxBy 聚合的功能。取最大值，其它字段用具有最大值的整个元素。
                if (value1.f2 > value2.f2) {
                    value1.setField(value1.f2, 2);
                    return value1;
                } else {
                    value2.setField(value2.f2, 2);
                    return value2;
                }
            }
        });
        reduce.print();

        env.execute();
    }
}
