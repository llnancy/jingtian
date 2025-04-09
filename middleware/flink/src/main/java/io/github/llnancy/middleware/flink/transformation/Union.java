package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * union 联合
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/15
 */
public class Union {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Integer> dss1 = env.fromData(1, 2, 3, 4, 5);
        DataStreamSource<Integer> dss2 = env.fromData(6, 7, 8, 9, 10);
        DataStreamSource<Integer> dss3 = env.fromData(11, 12, 13, 14, 15);

        // 联合
        dss1.union(dss2, dss3).print();

        env.execute();
    }
}
