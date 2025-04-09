package io.github.llnancy.middleware.flink.state;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * ReducingState
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class ReducingStateDemo {

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
                     * 定义归约状态
                     */
                    private ReducingState<Integer> sumTimestampReducingState;

                    @Override
                    public void open(OpenContext openContext) throws Exception {
                        super.open(openContext);
                        /*
                         在 open 方法中初始化状态
                         状态描述器 ReducingStateDescriptor 的参数：
                         1. name：状态名，不重复即可
                         2. reduceFunction：归约函数
                         2. typeInfo：值类型信息
                         */
                        sumTimestampReducingState = getRuntimeContext().getReducingState(
                                new ReducingStateDescriptor<>(
                                        "timestamp-reducing",
                                        (ReduceFunction<Integer>) Integer::sum,
                                        Types.INT
                                )
                        );
                    }

                    @Override
                    public void processElement(Tuple2<String, Integer> value, KeyedProcessFunction<String, Tuple2<String, Integer>, String>.Context ctx, Collector<String> out) throws Exception {
                        sumTimestampReducingState.add(value.f1);
                        out.collect(value.f0 + "=" +sumTimestampReducingState.get());
                    }
                })
                .print();

        env.execute();
    }
}
