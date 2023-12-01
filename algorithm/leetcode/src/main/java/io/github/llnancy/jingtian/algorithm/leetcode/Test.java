package io.github.llnancy.jingtian.algorithm.leetcode;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/25
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String path = "/Users/llnancy/workspace/llnancy-projects/llnancy-notes/docs/algorithm/leetcode/medium";
        File dir = new File(path);
        List<String> res = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    res.add(file.getName());
                }
            }
        }
        List<String> collect = res.stream()
                .sorted(Comparator.comparing(el -> el.substring(0, 2)))
                .collect(Collectors.toList());
        for (String s : collect) {
//            System.out.println("- [" + s.substring(5, s.length() - 3) + "](" + s.replaceAll(" ", "%20") + ")");
            System.out.println("'" + s + "',");
        }
    }
}
