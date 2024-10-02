package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * map transformation
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/22
 */
public class MapTransformation {

    public static void main(String[] args) throws Exception {
        // 将数据流中元素数值加倍
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromData(1, 2, 3)
                .map(new MapFunction<Integer, Integer>() {
                    @Override
                    public Integer map(Integer value) throws Exception {
                        return value * 2;
                    }
                })
                .print();
        env.execute();
    }
}
