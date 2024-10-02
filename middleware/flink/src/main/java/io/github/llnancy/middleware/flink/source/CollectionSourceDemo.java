package io.github.llnancy.middleware.flink.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * 从内存集合中读取
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/20
 */
public class CollectionSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从内存集合中读取
        DataStreamSource<Integer> dss = env.fromData(Arrays.asList(1, 2, 3));

        // 简写：直接传递元素序列
        // DataStreamSource<Integer> dss = env.fromData(1, 2, 3);

        dss.print();

        env.execute();
    }
}
