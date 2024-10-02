package io.github.llnancy.middleware.flink.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从数据生成器中读取
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/22
 */
public class DataGenSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度
        env.setParallelism(1);

        DataGeneratorSource<String> dataGeneratorSource = new DataGeneratorSource<>(
                new GeneratorFunction<Long, String>() {
                    @Override
                    public String map(Long value) throws Exception {
                        return "generated " + value;
                    }
                }, // 生成数据的方法
                Long.MAX_VALUE, // 最大数量
                RateLimiterStrategy.perSecond(10), // 限流策略：每秒生成 10 条数据
                Types.STRING // 生成数据的类型
        );
        DataStreamSource<String> dss = env.fromSource(dataGeneratorSource, WatermarkStrategy.noWatermarks(), "data-gen source");

        dss.print();
        env.execute();
    }
}
