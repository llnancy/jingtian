package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * 区间列表的交集
 * <a href="https://leetcode.cn/problems/interval-list-intersections/description/">https://leetcode.cn/problems/interval-list-intersections/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/8
 */
public class IntervalListIntersections {

    /*
    用两个指针在区间列表中迭代。存在交集的情况为不存在交集的情况取反，区间交集为 [起始位置最大值，结束位置最小值]，哪个区间小指针就往前移动。
     */

    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int p1 = 0;
        int p2 = 0;
        List<int[]> res = new ArrayList<>();
        while (p1 < firstList.length && p2 < secondList.length) {
            int a1 = firstList[p1][0];
            int a2 = firstList[p1][1];
            int b1 = secondList[p2][0];
            int b2 = secondList[p2][1];
            // 不存在交集的情况：b1 > a2 or a1 > b2
            // 取反则为存在交集的情况：b1 <= a2 and a1 <= b2
            if (b1 <= a2 && a1 <= b2) {
                // 区间交集为：[起始位置最大值，结束位置最小值]
                res.add(new int[] {Math.max(a1, b1), Math.min(a2, b2)});
            }
            // 指针前进
            if (b2 < a2) {
                p2++;
            } else {
                p1++;
            }
        }
        return res.toArray(new int[0][0]);
    }
}
