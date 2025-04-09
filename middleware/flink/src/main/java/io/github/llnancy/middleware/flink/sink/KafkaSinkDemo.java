package io.github.llnancy.middleware.flink.sink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.producer.ProducerConfig;

/**
 * kafka sink
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/16
 */
public class KafkaSinkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 如果需要精准一次 EXACTLY_ONCE，则必须开启 checkpoint。
        env.enableCheckpointing(2000, CheckpointingMode.EXACTLY_ONCE);

        DataStreamSource<String> dss = env.socketTextStream("localhost", 9999);

        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                // 指定 kafka 地址
                .setBootstrapServers("localhost:9092")
                // 指定序列化器：消息 topic 和序列化方式等
                .setRecordSerializer(
                        KafkaRecordSerializationSchema.<String>builder()
                                .setTopic("flink-kafka-sink-topic")
                                .setValueSerializationSchema(new SimpleStringSchema())
                                .build()
                )
                // 指定消息投递一致性级别：精准一次 or 至少一次
                .setDeliveryGuarantee(DeliveryGuarantee.EXACTLY_ONCE)
                // 精准一次的情况下，必须设置事务前缀
                .setTransactionalIdPrefix("flink-kafka-sink-transactional-")
                // 精准一次的情况下，必须设置事务超时时间，要求：大于 checkpoint 间隔，并小于最大值 15 分钟。
                .setProperty(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 10 * 60 * 1000 + "")
                .build();

        dss.sinkTo(kafkaSink);
        env.execute();
    }
}
