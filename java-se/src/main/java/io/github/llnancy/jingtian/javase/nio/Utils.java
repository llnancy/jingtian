package io.github.llnancy.jingtian.javase.nio;

import java.util.Objects;

/**
 * 工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
public class Utils {

    public static String path(Class<?> clazz, String fileName) {
        return Objects.requireNonNull(clazz.getResource(""))
                .getPath()
                .replace("target/classes", "src/main/java") + fileName;
    }
}
