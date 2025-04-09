package io.github.llnancy.middleware.flink.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从文件中读取
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/20
 */
public class FileSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);

        FileSource<String> fileSource = FileSource.forRecordStreamFormat(
                        new TextLineInputFormat(),
                        new Path("middleware/flink/file.txt")
                )
                .build();
        DataStreamSource<String> dss = env.fromSource(fileSource, WatermarkStrategy.noWatermarks(), "file source");

        dss.print();
        env.execute();
    }
}
