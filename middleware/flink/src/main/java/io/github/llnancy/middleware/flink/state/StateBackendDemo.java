package io.github.llnancy.middleware.flink.state;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.StateBackendOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * state backend
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class StateBackendDemo {

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.set(StateBackendOptions.STATE_BACKEND, "hashmap");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(conf);
    }
}
