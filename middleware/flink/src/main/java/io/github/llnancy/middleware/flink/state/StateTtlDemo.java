package io.github.llnancy.middleware.flink.state;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * State TTL
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class StateTtlDemo {

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
                     * 定义值状态
                     */
                    private ValueState<Integer> lastTimestampValueState;

                    @Override
                    public void open(OpenContext openContext) throws Exception {
                        super.open(openContext);
                        // 创建 StateTtlConfig 对象
                        StateTtlConfig stateTtlConfig = StateTtlConfig.newBuilder(Duration.ofSeconds(5L)) // 设定状态过期时间为 5 秒
                                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite) // 状态创建和写入时更新过期时间
                                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired) // 不返回过期的状态值
                                .build();
                        ValueStateDescriptor<Integer> vsd = new ValueStateDescriptor<>("last-timestamp", Types.INT);
                        // 状态描述器启用 TTL
                        vsd.enableTimeToLive(stateTtlConfig);
                        lastTimestampValueState = getRuntimeContext().getState(vsd);
                    }

                    @Override
                    public void processElement(Tuple2<String, Integer> value, KeyedProcessFunction<String, Tuple2<String, Integer>, String>.Context ctx, Collector<String> out) throws Exception {
                        // 读取值状态
                        Integer lastTimestamp = lastTimestampValueState.value();
                        if (lastTimestamp != null) {
                            Integer ts = value.f1;
                            if (Math.abs(ts - lastTimestamp) > 10) {
                                out.collect(value.f0 + ", " + value.f1 + " 与上一条数据 " + lastTimestamp + " 大小相差超过 10");
                            }
                        }
                        // 更新状态
                        lastTimestampValueState.update(value.f1);
                        // 清除状态
                        // lastTimestampValueState.clear();
                    }
                })
                .print();

        env.execute();
    }
}
