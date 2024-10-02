package io.github.llnancy.middleware.flink.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从 Socket 中读取
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/20
 */
public class SocketSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从 Socket 9999 端口按行读取数据
        DataStreamSource<String> dss = env.socketTextStream("localhost", 9999);

        dss.print();
        env.execute();
    }
}
