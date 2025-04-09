package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * 分流：旁路输出
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/15
 */
public class SideOutput {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Integer> dss = env.fromData(-1, 0, 1, 2, 3, 4, 5, 6);

        // 将流拆分为：正奇数流、正偶数流和负数流。
        // 定义奇数和偶数 OutputTag
        OutputTag<Integer> oddTag = new OutputTag<Integer>("side-odd") {};
        OutputTag<Integer> evenTag = new OutputTag<Integer>("side-even") {};

        SingleOutputStreamOperator<Integer> process = dss.process(new ProcessFunction<Integer, Integer>() {

            private static final long serialVersionUID = 8382667702819050654L;

            @Override
            public void processElement(Integer value, ProcessFunction<Integer, Integer>.Context ctx, Collector<Integer> out) throws Exception {
                if (value > 0) {
                    if (value % 2 == 0) {
                        // 正偶数流
                        ctx.output(evenTag, value);
                    } else {
                        // 正奇数流
                        ctx.output(oddTag, value);
                    }
                } else {
                    // 主流：负数流
                    out.collect(value);
                }
            }
        });

        // 主流
        process.print("主流：");

        // 获取旁路输出流
        process.getSideOutput(oddTag).print("奇数旁路输出流：");
        process.getSideOutput(evenTag).print("偶数旁路输出流：");

        env.execute();
    }
}
