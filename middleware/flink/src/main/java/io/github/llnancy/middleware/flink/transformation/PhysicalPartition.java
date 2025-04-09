package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 物理分区
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/15
 */
public class PhysicalPartition {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataStreamSource<Integer> dss = env.fromData(1, 2, 3, 4, 5, 6);

        // 随机分区。随机且均匀。
        dss.shuffle().print();

        // 轮询分区。当数据源的数据发生倾斜时，可用轮询分区解决。
        // dss.rebalance().print();

        // 重缩放分区。局部轮询。
        // dss.rescale().print();

        // 广播
        // dss.broadcast().print();

        // 全局分区。全部发送至下游算子的第一个并行子任务。
        // dss.global().print();

        // 自定义分区
        dss.partitionCustom(
                // 根据分区 key 实现分区逻辑
                new Partitioner<String>() {
                    @Override
                    public int partition(String key, int numPartitions) {
                        // numPartitions：下游分区数
                        // 这里用简单取模逻辑演示
                        return Integer.parseInt(key) % numPartitions;
                    }
                },
                // 从数据流中提取分区 key
                new KeySelector<Integer, String>() {
                    @Override
                    public String getKey(Integer value) throws Exception {
                        return value.toString();
                    }
                }
        );
        env.execute();
    }
}
