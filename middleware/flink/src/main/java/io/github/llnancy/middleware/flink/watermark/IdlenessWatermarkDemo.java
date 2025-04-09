package io.github.llnancy.middleware.flink.watermark;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * watermark idleness
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/25
 */
public class IdlenessWatermarkDemo {

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

        dss.assignTimestampsAndWatermarks(
                        WatermarkStrategy.<Tuple2<String, Integer>>forMonotonousTimestamps()
                                .withTimestampAssigner((element, recordTimestamp) -> element.f1 * 1000L)
                                .withIdleness(Duration.ofMinutes(1)) // 设置水位线空闲等待超时时间
                )
                .print();

        env.execute();
    }
}
