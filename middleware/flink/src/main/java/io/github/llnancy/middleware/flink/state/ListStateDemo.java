package io.github.llnancy.middleware.flink.state;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
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

/**
 * ListState
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class ListStateDemo {

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
                     * 定义列表状态
                     */
                    private ListState<Integer> timestampListState;

                    @Override
                    public void open(OpenContext openContext) throws Exception {
                        super.open(openContext);
                        /*
                         在 open 方法中初始化状态
                         状态描述器 ListStateDescriptor 的参数：
                         1. name：状态名，不重复即可
                         2. typeInfo：列表状态中存储的元素的类型信息
                         */
                        timestampListState = getRuntimeContext().getListState(new ListStateDescriptor<>("timestamp-list", Types.INT));
                    }

                    @Override
                    public void processElement(Tuple2<String, Integer> value, KeyedProcessFunction<String, Tuple2<String, Integer>, String>.Context ctx, Collector<String> out) throws Exception {
                        // 将当前数据添加至列表状态
                        timestampListState.add(value.f1);
                        // 查询当前列表状态
                        Iterable<Integer> lastTimestampList = timestampListState.get();
                        /*
                        只保留列表状态中三个最大的元素
                         */
                        List<Integer> list = new ArrayList<>();
                        lastTimestampList.forEach(list::add);
                        // 降序
                        list.sort(Collections.reverseOrder());
                        if (list.size() > 3) { // 最大为 4
                            // 移除最后一个元素
                            list.remove(3);
                        }
                        out.collect(value.f0 + ", 最大的三个元素：" + list.toString());
                        // 覆盖更新列表状态
                        timestampListState.update(list);
                    }
                })
                .print();

        env.execute();
    }
}
