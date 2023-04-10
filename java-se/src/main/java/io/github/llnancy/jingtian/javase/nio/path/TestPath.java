package io.github.llnancy.jingtian.javase.nio.path;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path And Paths
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/13
 */
@Slf4j
public class TestPath {

    public static void main(String[] args) {
        // 相对路径 相对于user.dir环境变量路径
        // 当IDE为IntelliJ IDEA时，如果IDEA中打开的是单个项目则不用加项目名，如果IDEA中打开的是单个项目的上一级目录，则需要加上项目名sunchaser-sparrow/
        Path path1 = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/path/TestPath.java");

        // 绝对路径 代表 /Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/java-se/src/main/java/com/sunchaser/sparrow/javase/nio/path/TestPath.java 文件
        Path path2 = Paths.get("/Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/java-se/src/main/java/com/sunchaser/sparrow/javase/nio/path/TestPath.java");

        // 绝对路径 代表 /Users/sunchaser/workspace/idea-projects 文件夹
        Path path3 = Paths.get("/Users/sunchaser/workspace", "idea-projects");

        LOGGER.info("path1={}, path2={}, path3={}", path1, path2, path3);

        Path path = Paths.get("/Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/java-se/src/../java9");
        // path=/Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/java-se/src/../java9
        // path.normalize()=/Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/java-se/java9
        // path.normalize()：正常化路径。
        LOGGER.info("\npath={}\npath.normalize()={}", path, path.normalize());
    }
}
