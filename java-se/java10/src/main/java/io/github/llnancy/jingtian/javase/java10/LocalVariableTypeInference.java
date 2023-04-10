package io.github.llnancy.jingtian.javase.java10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 局部变量类型推断
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/11
 */
public class LocalVariableTypeInference {

    public static void main(String[] args) {
        // Java7 之前
        ArrayList<String> list1 = new ArrayList<String>();

        // Java7 类型推断，根据左边类型推断右边
        ArrayList<String> list2 = new ArrayList<>();

        // 返回值包含复杂泛型结构
        LinkedHashSet<Map.Entry<String, String>> set = new LinkedHashSet<>();
        Iterator<Map.Entry<String, String>> iterator = set.iterator();
        // Iterator i = set.iterator(); // 编译器报黄色警告

        // Java10 类型推断
        // 声明变量时，根据等号右边的类型推断左边
        var list3 = new ArrayList<Integer>();
        var str = "var string";
        var num = 1;
        var d = 1.0;

        System.out.println(str);
        System.out.println(num);
        System.out.println(d);

        // 接收复杂泛型结构的返回值
        var iter = set.iterator();

        // forEach 循环中使用
        list2.add("java");
        list2.add("go");
        list2.add("python");
        for (var s : list2) {
            System.out.println(s);
            System.out.println(s.getClass());
        }

        // 普通 for 循环中使用
        list3.add(1);
        list3.add(3);
        list3.add(5);
        for (var i = 0; i < list3.size(); i++) {
            System.out.println(list3.get(i));
            System.out.println(list3.get(i).getClass());
        }
    }
}
