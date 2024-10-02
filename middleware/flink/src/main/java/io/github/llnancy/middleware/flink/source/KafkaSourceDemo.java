package io.github.llnancy.middleware.flink.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从 Kafka 中读取
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/22
 */
public class KafkaSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092") // 设置 kafka 服务地址
                .setGroupId("flink") // 设置消费者组
                .setTopics("flink-kafka-source-topic") // 指定消费 topic
                .setValueOnlyDeserializer(new SimpleStringSchema()) // 指定消息内容反序列化器
                .setStartingOffsets(OffsetsInitializer.latest()) // 指定 flink 消费 kafka 的 offset 策略
                .build();
        DataStreamSource<String> dss = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafka source");

        dss.print();
        env.execute();
    }
}
