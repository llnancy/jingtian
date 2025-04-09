package io.github.llnancy.middleware.flink.watermark;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 自定义周期性水位线生成器
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/24
 */
public class CustomPeriodicWatermarkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Tuple2<String, Integer>> dss = env.fromData(
                Tuple2.of("a", 1),
                Tuple2.of("a", 2),
                Tuple2.of("a", 10),
                Tuple2.of("a", 11),
                Tuple2.of("b", 2),
                Tuple2.of("b", 3)
        );
        CustomWatermarkStrategy<Tuple2<String, Integer>> customWs = new CustomWatermarkStrategy<>(5L);
        customWs.withTimestampAssigner((element, recordTimestamp) -> {
            System.out.println("extractTimestamp 数据：" + element + ", recordTimestamp = " + recordTimestamp);
            // 从数据中提取事件时间戳，返回值的单位为毫秒。
            return element.f1 * 1000L;
        });
        dss.assignTimestampsAndWatermarks(customWs)
                .print();
        env.execute();
    }

    public static class CustomWatermarkStrategy<T> implements WatermarkStrategy<T> {

        private static final long serialVersionUID = -3776470895873779964L;

        private final long outOfOrderMillis;

        public CustomWatermarkStrategy(long outOfOrderMillis) {
            this.outOfOrderMillis = outOfOrderMillis;
        }

        @Override
        public WatermarkGenerator<T> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
            return new CustomPeriodicWatermarkGenerator<>(outOfOrderMillis);
        }
    }

    /**
     * 参考 org.apache.flink.api.common.eventtime.BoundedOutOfOrdernessWatermarks#BoundedOutOfOrdernessWatermarks(java.time.Duration) 实现
     *
     * @param <T>
     */
    public static class CustomPeriodicWatermarkGenerator<T> implements WatermarkGenerator<T> {

        /**
         * 当前观察到的最大事件时间戳
         */
        private long maxTimestamp;

        /**
         * 延迟时间
         */
        private final long outOfOrderMillis;

        public CustomPeriodicWatermarkGenerator(long outOfOrderMillis) {
            this.outOfOrderMillis = outOfOrderMillis;
            this.maxTimestamp = Long.MIN_VALUE + outOfOrderMillis + 1;
        }

        /**
         * 每条数据来都会调用一次，用来提取当前最大事件时间。
         */
        @Override
        public void onEvent(T event, long eventTimestamp, WatermarkOutput output) {
            maxTimestamp = Math.max(maxTimestamp, eventTimestamp);
        }

        /**
         * 周期性调用，生成 Watermark
         */
        @Override
        public void onPeriodicEmit(WatermarkOutput output) {
            // 发出的 watermark = 当前最大事件时间戳 - 最大乱序延迟时间
            output.emitWatermark(new Watermark(maxTimestamp - outOfOrderMillis - 1L));
        }
    }
}
