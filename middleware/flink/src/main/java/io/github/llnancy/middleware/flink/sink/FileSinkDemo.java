package io.github.llnancy.middleware.flink.sink;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.time.Duration;
import java.time.ZoneId;


/**
 * file sink
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/16
 */
public class FileSinkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 必须开启 checkpoint，设置精准一次 EXACTLY_ONCE，否则文件一直都是 .inprogress 状态。
        env.enableCheckpointing(2000, CheckpointingMode.EXACTLY_ONCE);

        // 用数据生成器生成无界流
        DataGeneratorSource<String> dgs = new DataGeneratorSource<>(
                new GeneratorFunction<Long, String>() {
                    @Override
                    public String map(Long value) throws Exception {
                        return "generated " + value;
                    }
                },
                Long.MAX_VALUE,
                RateLimiterStrategy.perSecond(10),
                Types.STRING
        );
        DataStreamSource<String> dss = env.fromSource(dgs, WatermarkStrategy.noWatermarks(), "data-gen-source");

        FileSink<String> fileSink = FileSink.<String>forRowFormat(
                        new Path("/Users/llnancy/workspace/llnancy-projects/jingtian/middleware/flink/src/main/resources"), // 输出文件的路径
                        new SimpleStringEncoder<>() // 行编码器
                )
                // 配置输出文件的前后缀
                .withOutputFileConfig(
                        OutputFileConfig.builder()
                                .withPartPrefix("flink-file-sink")
                                .withPartSuffix(".log")
                                .build()
                )
                // 配置分桶策略：每小时一个桶（文件夹）
                .withBucketAssigner(new DateTimeBucketAssigner<>("yyyy-MM-dd--HH", ZoneId.systemDefault()))
                // 配置文件滚动策略：1 分钟或者文件达到 1MB 后将 inprogress 状态的文件进行滚动（创建一个新文件）
                .withRollingPolicy(
                        DefaultRollingPolicy.builder()
                                .withRolloverInterval(Duration.ofMillis(1))
                                .withMaxPartSize(MemorySize.ofMebiBytes(1))
                                .build()
                )
                .build();
        dss.sinkTo(fileSink);
        env.execute();
    }

}
