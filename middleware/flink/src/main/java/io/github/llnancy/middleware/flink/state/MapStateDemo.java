package io.github.llnancy.middleware.flink.state;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * MapState
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class MapStateDemo {

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
                     * 定义映射状态
                     */
                    private MapState<Integer, Integer> timestampMapState;

                    @Override
                    public void open(OpenContext openContext) throws Exception {
                        super.open(openContext);
                        /*
                         在 open 方法中初始化状态
                         状态描述器 MapStateDescriptor 的参数：
                         1. name：状态名，不重复即可
                         2. keyTypeInfo：映射状态中键的类型信息
                         3. valueTypeInfo：映射状态中值的类型信息
                         */
                        timestampMapState = getRuntimeContext().getMapState(new MapStateDescriptor<>("timestamp-map", Types.INT, Types.INT));
                    }

                    @Override
                    public void processElement(Tuple2<String, Integer> value, KeyedProcessFunction<String, Tuple2<String, Integer>, String>.Context ctx, Collector<String> out) throws Exception {
                        Integer timestamp = value.f1;
                        if (timestampMapState.contains(timestamp)) {
                            Integer count = timestampMapState.get(timestamp);
                            timestampMapState.put(timestamp, ++count);
                        } else {
                            timestampMapState.put(timestamp, 1);
                        }
                        // 遍历映射状态
                        StringBuilder sb = new StringBuilder();
                        for (Map.Entry<Integer, Integer> entry : timestampMapState.entries()) {
                            // Integer k = entry.getKey();
                            // Integer v = entry.getValue();
                            sb.append(entry).append(", ");
                        }
                        out.collect(sb.toString());
                    }
                })
                .print();

        env.execute();
    }
}
