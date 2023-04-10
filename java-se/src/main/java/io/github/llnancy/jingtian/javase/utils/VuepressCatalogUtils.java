package io.github.llnancy.jingtian.javase.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.URLEncodeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * vuepress catalog utils
 *
 * @author llnancy
 * @since JDK8 2023/4/6
 */
public class VuepressCatalogUtils {

    public static void main(String[] args) {
        if (ArrayUtils.isEmpty(args)) {
            return;
        }
        String path = args[0];
        printCatalog(path);
        printSidebar(path);
    }

    public static void printSidebar(String path) {
        List<String> sidebar = generateSidebar(path);
        for (String s : sidebar) {
            System.out.println(s + ",");
        }
    }

    public static void printCatalog(String path) {
        List<String> catalog = generateCatalog(path);
        for (String s : catalog) {
            System.out.println(s);
        }
    }

    public static List<String> generateSidebar(String path) {
        List<File> files = FileUtil.loopFiles(path);
        if (CollectionUtils.isEmpty(files)) {
            return Collections.emptyList();
        }
        return files.stream()
                .filter(file -> !file.getName().equals("README.md"))
                .sorted(Comparator.comparing(file -> file.getName().substring(0, 2)))
                .map(file -> "'" + file.getName() + "'")
                .collect(Collectors.toList());
    }

    public static List<String> generateCatalog(String path) {
        return generateCatalog(path, s -> s.substring(5, s.length() - 3));
    }

    public static List<String> generateCatalog(String path, Function<String, String> catalogItemHandler) {
        List<File> files = FileUtil.loopFiles(path);
        if (CollectionUtils.isEmpty(files)) {
            return Collections.emptyList();
        }
        return files.stream()
                .filter(file -> !file.getName().equals("README.md"))
                .sorted(Comparator.comparing(file -> file.getName().substring(0, 2)))
                .map(file -> "- [" + catalogItemHandler.apply(file.getName()) + "](" + URLEncodeUtil.encode(file.getName()) + ")")
                .collect(Collectors.toList());
    }
}
