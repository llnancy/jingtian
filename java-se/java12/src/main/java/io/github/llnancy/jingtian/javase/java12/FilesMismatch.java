package io.github.llnancy.jingtian.javase.java12;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * {@link java.nio.file.Files#mismatch(Path, Path)}
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK12 2023/7/17
 */
public class FilesMismatch {

    public static void main(String[] args) {
        Path pathA = null;
        Path pathB = null;
        try {
            // 创建两个文件
            pathA = Files.createFile(Paths.get("a.txt"));
            pathB = Files.createFile(Paths.get("b.txt"));

            // 写入相同内容
            Files.write(pathA, "java12".getBytes(), StandardOpenOption.WRITE);
            Files.write(pathB, "java12".getBytes(), StandardOpenOption.WRITE);

            // 对比两个文件
            long mismatch = Files.mismatch(pathA, pathB);
            System.out.println(mismatch);

            // 追加不同内容
            Files.write(pathA, "123".getBytes(), StandardOpenOption.APPEND);
            Files.write(pathB, "321".getBytes(), StandardOpenOption.APPEND);

            // 对比两个文件
            mismatch = Files.mismatch(pathA, pathB);
            System.out.println(mismatch);
        } catch (Exception ignore) {
            // ignore
        } finally {
            if (Objects.nonNull(pathA)) {
                pathA.toFile().deleteOnExit();
            }
            if (Objects.nonNull(pathB)) {
                pathB.toFile().deleteOnExit();
            }
        }
    }
}
