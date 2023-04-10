package io.github.llnancy.jingtian.javase.nio.files;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test Files walkFileTree
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/13
 */
@Slf4j
public class TestFilesWalkFileTree {

    public static void main(String[] args) throws IOException {
        // 文件夹数量
        AtomicInteger dirCount = new AtomicInteger();
        // 文件数量
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("sunchaser-sparrow/java-se"), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (Files.isDirectory(dir)) {
                    LOGGER.info("preVisitDirectory=====>{}", dir);
                    // 文件夹数量加一
                    dirCount.incrementAndGet();
                }
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                LOGGER.info("visitFile=====>{}", file);
                // 文件数量加一
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                LOGGER.info("visitFileFailed=====>{}", file);
                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });

        LOGGER.info("dirCount={}, fileCount={}", dirCount, fileCount);
    }
}
