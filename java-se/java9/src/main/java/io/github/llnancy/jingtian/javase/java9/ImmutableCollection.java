package io.github.llnancy.jingtian.javase.java9;

import java.util.*;

/**
 * List Map Set 容器类新增 of() 方法
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public class ImmutableCollection {

    public static void main(String[] args) {
        createImmutableCollectionBeforeJava9();

        createImmutableCollectionInJava9();
    }

    private static void createImmutableCollectionInJava9() {
        // 创建不可变 List
        List<String> list = List.of("java", "python", "go");
        System.out.println(list);
        // 创建不可变 Set
        Set<String> set = Set.of("java", "python", "go");
        System.out.println(set);
        // 创建不可变 Map
        Map<String, String> map1 = Map.of(
                "java", "1",
                "python", "2",
                "go", "3"
        );
        System.out.println(map1);
        Map<String, String> map2 = Map.ofEntries(
                Map.entry("java", "1"),
                Map.entry("python", "2"),
                Map.entry("go", "3")
        );
        System.out.println(map2);
    }

    private static void createImmutableCollectionBeforeJava9() {
        // 创建不可变 List 方式一
        List<String> list1 = new ArrayList<>();
        list1.add("java");
        list1.add("python");
        list1.add("go");
        list1 = Collections.unmodifiableList(list1);
        System.out.println(list1);

        // 创建不可变 List 方式二
        List<String> list2 = Arrays.asList("java", "python", "go");
        System.out.println(list2);

        // 创建不可变 Set
        Set<String> set = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("java", "python", "go")));
        System.out.println(set);

        // 创建不可变 Map
        Map<String, String> map = Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("java", "1");
                put("python", "2");
                put("go", "3");
            }
        });
        System.out.println(map);
    }
}
