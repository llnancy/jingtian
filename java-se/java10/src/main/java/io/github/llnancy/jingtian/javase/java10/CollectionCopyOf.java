package io.github.llnancy.jingtian.javase.java10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * List Map Set 容器类新增 copyOf 方法
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/15
 */
public class CollectionCopyOf {
    public static void main(String[] args) {
        copyOfList();

        copyOfSet();

        copyOfMap();
    }

    private static void copyOfMap() {
        Map<String, String> map = new HashMap<>();
        map.put("java", "1");
        map.put("python", "2");
        map.put("go", "3");
        Map<String, String> copyOfMap = Map.copyOf(map);
        for (Map.Entry<String, String> entry : copyOfMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        // 不可变 Map，无法修改
        // copyOfMap.put("php", "0");
    }

    private static void copyOfSet() {
        Set<String> set = new HashSet<>();
        set.add("java");
        set.add("python");
        set.add("go");
        Set<String> copyOfSet = Set.copyOf(set);
        for (String s : copyOfSet) {
            System.out.println(s);
        }
        // 不可变 Set，无法修改
        // copyOfSet.add("php");
    }

    private static void copyOfList() {
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("python");
        list.add("go");
        List<String> copyOfList = List.copyOf(list);
        for (String s : copyOfList) {
            System.out.println(s);
        }
        // 不可变 List，无法修改
        // copyOfList.add("php");
    }
}
