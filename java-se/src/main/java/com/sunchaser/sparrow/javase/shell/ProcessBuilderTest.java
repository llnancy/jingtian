package com.sunchaser.sparrow.javase.shell;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/23
 */
@Slf4j
public class ProcessBuilderTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c", "curl https://lilu.org.cn/");

        Map<String, String> env = new HashMap<>();
        env.put("env", "dev");
        builder.environment().putAll(env);

        builder.directory(new File("/Users/sunchaser/workspace/"));

        Process process = builder.start();

        List<String> execInfoList = new ArrayList<>();
        List<String> execErrList = new ArrayList<>();
        collectStreamInfo(process.getInputStream(), execInfoList);
        collectStreamInfo(process.getErrorStream(), execErrList);

        int waitFor = process.waitFor();
        process.destroy();
        System.out.println("process退出值：" + waitFor);
    }

    /**
     * 收集命令执行信息：将流中的信息一行一行读取存入List容器
     *
     * @param is        输入流
     * @param collector 收集器
     * @throws IOException exception
     */
    private static void collectStreamInfo(InputStream is, List<String> collector) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            LOGGER.info("CommandExecutor collectStreamInfo: " + line);
            collector.add(line);
        }
    }

}
