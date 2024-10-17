package io.github.llnancy.middleware.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * DataSet API demo
 * deprecated and not recommended.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/15
 */
public class DataSetApiWordCountDemo {

    public static void main(String[] args) throws Exception {
        // 1. 获取执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 2. 从集合中读取源数据
        DataSource<String> ds = env.fromCollection(Arrays.asList("hello world", "hello java", "hello flink"));
        // 3. 切分、转换成 Tuple2。注意这里不能直接使用 lambda 表达式。
        FlatMapOperator<String, Tuple2<String, Integer>> operator = ds.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                String[] words = value.split(" ");
                for (String word : words) {
                    out.collect(Tuple2.of(word, 1));
                }
            }
        });
        // 4. 按 Tuple 第 0 个元素分组
        UnsortedGrouping<Tuple2<String, Integer>> group = operator.groupBy(0);
        // 5. 按 Tuple 第 1 个元素累加
        AggregateOperator<Tuple2<String, Integer>> sum = group.sum(1);
        // 6. 打印结果
        sum.print();
    }
}
