package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import java.util.Arrays;

/**
 * 删除被覆盖区间
 * <a href="https://leetcode.cn/problems/remove-covered-intervals/description/">https://leetcode.cn/problems/remove-covered-intervals/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/8
 */
public class RemoveCoveredIntervals {

    /*
    首先按区间起点进行升序排序，起点相同时按终点降序排序。然后遍历区间，相邻区间可能出现三种情况：
    1. 找到覆盖区间
    2. 找到相交区间，两个区间可以合并成一个大区间
    3. 两个区间完全不相交
     */

    public int removeCoveredIntervals(int[][] intervals) {
        // 按起点升序排序，起点相同时按终点降序排序
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        // 记录合并区间的起点和终点
        int left = intervals[0][0];
        int right = intervals[0][1];

        // 记录被删除的覆盖区间数量
        int res = 0;
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            // 找到覆盖区间
            if (left <= interval[0] && right >= interval[1]) {
                res++;
            }
            // 找到相交区间，合并
            if (right >= interval[0] && right <= interval[1]) {
                right = interval[1];
            }
            // 完全不相交，更新起点和终点
            if (right < interval[0]) {
                left = interval[0];
                right = interval[1];
            }
        }
        // 返回剩余区间数量
        return intervals.length - res;
    }
}
