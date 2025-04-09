package io.github.llnancy.middleware.flink.state;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * AggregatingState
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class AggregatingStateDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Tuple2<String, Integer>> dss = env.fromData(
                Tuple2.of("a", 1),
                Tuple2.of("a", 13),
                Tuple2.of("a", 2),
                Tuple2.of("a", 14),
                Tuple2.of("b", 3),
                Tuple2.of("b", 15)
        );

        dss.assignTimestampsAndWatermarks(
                        WatermarkStrategy.<Tuple2<String, Integer>>forBoundedOutOfOrderness(Duration.ofSeconds(5L))
                                .withTimestampAssigner((element, recordTimestamp) -> element.f1 * 1000L)
                )
                .keyBy(value -> value.f0)
                .process(new KeyedProcessFunction<String, Tuple2<String, Integer>, String>() {

                    private static final long serialVersionUID = 6757886376897953500L;

                    /**
                     * 定义聚合状态
                     */
                    private AggregatingState<Integer, Double> avgTimestampAggregatingState;

                    @Override
                    public void open(OpenContext openContext) throws Exception {
                        super.open(openContext);
                        /*
                         在 open 方法中初始化状态
                         状态描述器 AggregatingStateDescriptor 的参数：
                         1. name：状态名，不重复即可
                         2. aggFunction：聚合函数
                         2. stateType：聚合器类型信息
                         */
                        avgTimestampAggregatingState = getRuntimeContext().getAggregatingState(
                                new AggregatingStateDescriptor<>(
                                        "timestamp-aggregating",
                                        new AggregateFunction<Integer, Tuple2<Integer, Integer>, Double>() {

                                            private static final long serialVersionUID = 8580070759245617855L;

                                            @Override
                                            public Tuple2<Integer, Integer> createAccumulator() {
                                                return Tuple2.of(0, 0);
                                            }

                                            @Override
                                            public Tuple2<Integer, Integer> add(Integer value, Tuple2<Integer, Integer> accumulator) {
                                                return Tuple2.of(accumulator.f0 + value, accumulator.f1 + 1);
                                            }

                                            @Override
                                            public Double getResult(Tuple2<Integer, Integer> accumulator) {
                                                // 计算平均值
                                                return accumulator.f0 * 1D / accumulator.f1;
                                            }

                                            @Override
                                            public Tuple2<Integer, Integer> merge(Tuple2<Integer, Integer> a, Tuple2<Integer, Integer> b) {
                                                // 不会被调用
                                                return null;
                                            }
                                        },
                                        Types.TUPLE(Types.INT, Types.DOUBLE)
                                )
                        );
                    }

                    @Override
                    public void processElement(Tuple2<String, Integer> value, KeyedProcessFunction<String, Tuple2<String, Integer>, String>.Context ctx, Collector<String> out) throws Exception {
                        // 将当前元素加入聚合状态
                        avgTimestampAggregatingState.add(value.f1);
                        // 从聚合状态中读取值
                        Double avg = avgTimestampAggregatingState.get();
                        out.collect(value.f0 + ": " + avg);
                    }
                })
                .print();

        env.execute();
    }
}
