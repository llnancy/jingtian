package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * filter transformation
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/22
 */
public class FilterTransformation {

    public static void main(String[] args) throws Exception {
        // 筛选值不为零的元素
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromData(0, 1, 0, 2, 0, 3)
                .filter(new FilterFunction<Integer>() {
                    @Override
                    public boolean filter(Integer value) throws Exception {
                        return value != 0;
                    }
                })
                .print();
        env.execute();
    }
}
