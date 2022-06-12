package com.sunchaser.sparrow.javase.nio;

import java.util.Objects;

/**
 * 工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
public class Utils {

    public static String path(String fileName) {
        return Objects.requireNonNull(Utils.class.getResource(""))
                .getPath()
                .replace("target/classes", "src/main/java") + fileName;
    }
}
