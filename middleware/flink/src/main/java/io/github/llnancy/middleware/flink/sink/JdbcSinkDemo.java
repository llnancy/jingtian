package io.github.llnancy.middleware.flink.sink;

import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * jdbc demo
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/17
 */
public class JdbcSinkDemo {

    static class FlinkJdbcSinkBean {

        private Long id;

        private String name;

        private String description;

        public FlinkJdbcSinkBean() {
        }

        public FlinkJdbcSinkBean(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<FlinkJdbcSinkBean> dss = env.fromData(
                new FlinkJdbcSinkBean(1L, "flink", "flink desc"),
                new FlinkJdbcSinkBean(2L, "jdbc", "jdbc desc"),
                new FlinkJdbcSinkBean(3L, "sink", "sink desc")
        );

        SinkFunction<FlinkJdbcSinkBean> jdbcSink = JdbcSink.sink(
                // 占位符语法
                "insert into flink_jdbc_sink(`id`, `name`, `desc`) values(?, ?, ?)",
                new JdbcStatementBuilder<FlinkJdbcSinkBean>() {

                    @Override
                    public void accept(PreparedStatement preparedStatement, FlinkJdbcSinkBean bean) throws SQLException {
                        // 填充占位符
                        preparedStatement.setString(1, bean.getId().toString());
                        preparedStatement.setString(2, bean.getName());
                        preparedStatement.setString(3, bean.getDescription());
                    }
                },
                JdbcExecutionOptions.builder()
                        .withMaxRetries(3) // 重试次数
                        .withBatchSize(100) // 批次大小
                        .withBatchIntervalMs(3000) // 批次时间
                        .build(),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:mysql://localhost:3306/flink_jdbc_sink") // jdbc 连接
                        .withUsername("root") // 账号
                        .withPassword("123456") // 密码
                        .withConnectionCheckTimeoutSeconds(60) // 重试的超时时间，默认 60 秒
                        .build()
        );
        dss.addSink(jdbcSink);
        env.execute();
    }
}
