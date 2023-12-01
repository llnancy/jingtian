package io.github.llnancy.jingtian.javase.java9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/11
 */
public class DiamondOperator {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<>();
        /**
        Comparator<Integer> comparator = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
         **/
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
    }
}
