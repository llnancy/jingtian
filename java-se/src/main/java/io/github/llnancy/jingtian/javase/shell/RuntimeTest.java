package io.github.llnancy.jingtian.javase.shell;

import java.io.IOException;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/23
 */
public class RuntimeTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("curl https://lilu.org.cn/");
        int waitFor = process.waitFor();
        process.destroy();
        System.out.println("process退出值：" + waitFor);
    }
}
