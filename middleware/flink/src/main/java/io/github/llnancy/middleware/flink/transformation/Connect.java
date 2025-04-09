package io.github.llnancy.middleware.flink.transformation;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * connect 连接流
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/16
 */
public class Connect {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> dss1 = env.fromData(1, 2, 3, 4, 5);
        DataStreamSource<String> dss2 = env.fromData("java", "flink", "spring");

        // 连接流
        ConnectedStreams<Integer, String> connectedStreams = dss1.connect(dss2);

        // 接 map/flatMap/process 等操作，对应 CoMapFunction/CoFlatMapFunction/CoProcessFunction 接口。
        SingleOutputStreamOperator<String> result = connectedStreams.map(new CoMapFunction<Integer, String, String>() {

            private static final long serialVersionUID = -3202923220190753344L;

            @Override
            public String map1(Integer value) throws Exception {
                // 处理第一条流的数据
                return value.toString();
            }

            @Override
            public String map2(String value) throws Exception {
                // 处理第二条流的数据
                return value;
            }
        });

        result.print();

        env.execute();
    }
}
