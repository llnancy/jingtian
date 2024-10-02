package io.github.llnancy.middleware.flink;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * get environment
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/20
 */
public class DataStreamApiDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // LocalStreamEnvironment localEnvironment = StreamExecutionEnvironment.createLocalEnvironment();
        // StreamExecutionEnvironment remoteEnvironment = StreamExecutionEnvironment.createRemoteEnvironment("192.168.0.1", 8080, "/home/file.jar");

        // // 环境配置对象
        // Configuration configuration = new Configuration();
        // // 可自定义环境配置参数
        // configuration.set(RestOptions.BIND_PORT, "8081");
        // // 支持 Configuration 的重载方法
        // StreamExecutionEnvironment configuredEnv = StreamExecutionEnvironment.getExecutionEnvironment(configuration);
        // LocalStreamEnvironment configuredLocalEnvironment = StreamExecutionEnvironment.createLocalEnvironment(configuration);
        // StreamExecutionEnvironment configuredRemoteEnvironment = StreamExecutionEnvironment.createRemoteEnvironment("192.168.0.1", 8080, configuration, "/home/file.jar");

        // 设置执行模式
        // env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        // 同步阻塞等待作业完成，返回执行结果。
        JobExecutionResult result = env.execute();

        // // 异步。不会阻塞等待作业完成。
        // JobClient jobClient = env.executeAsync();
        // // 可自行阻塞获取执行结果。
        // JobExecutionResult result = jobClient.getJobExecutionResult().get();

    }
}
