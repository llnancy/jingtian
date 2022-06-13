package com.sunchaser.sparrow.javase.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Test Files
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/13
 */
@Slf4j
public class TestFiles {

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        // 当IDE为IntelliJ IDEA时，如果IDEA中打开的是单个项目则不用加项目名，如果IDEA中打开的是单个项目的上一级目录，则需要加上项目名sunchaser-sparrow/
        Path path = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/TestFiles.java");
        LOGGER.info("Files.exists(path)={}", Files.exists(path));

        // 创建一级目录
        try {
            Path dir = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/dir1");
            Files.createDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建多级目录
        try {
            Path dir = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/dir1/dir2/dir3");
            Files.createDirectories(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 拷贝文件
        try {
            Path source = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/source.txt");
            Path target = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/target.txt");
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 移动文件
        try {
            Path source = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/source.txt");
            Path target = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/dir1/target.txt");
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);// StandardCopyOption.ATOMIC_MOVE保证文件移动的原子性
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除文件
        try {
            Path target = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/target.txt");
            Files.delete(target);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除目录
        try {
            Path target = Paths.get("java-se/src/main/java/com/sunchaser/sparrow/javase/nio/dir1");
            Files.delete(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
