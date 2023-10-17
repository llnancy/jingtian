package io.github.llnancy.jingtian.javase.java14;

import java.util.ArrayList;

/**
 * instanceof
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK14 2023/7/19
 */
public class Instanceof {

    public static void main(String[] args) {
        // Java14 之前的写法
        Object obj = new ArrayList<>();
        if (obj instanceof ArrayList) {
            ArrayList<String> list = (ArrayList<String>) obj;
            list.add("instanceof");
        }
        System.out.println(obj);

        // Java14 写法
        Object obj2 = new ArrayList<>();
        if (obj2 instanceof ArrayList list) {
            list.add("instanceof");
        }
        System.out.println(obj2);
    }
}
