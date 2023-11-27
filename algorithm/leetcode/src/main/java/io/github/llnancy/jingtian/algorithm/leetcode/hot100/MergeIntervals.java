package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 合并区间
 * <a href="https://leetcode.cn/problems/merge-intervals/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/merge-intervals/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/8
 */
public class MergeIntervals {

    /*
    首先按区间的 start 升序排序，然后依次迭代，能合并的进行合并，否则加入结果集合。
     */

    public int[][] merge(int[][] intervals) {
        // 按区间的 start 升序排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        Deque<int[]> res = new LinkedList<>();
        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            int[] last = res.getLast();
            if (cur[0] <= last[1]) {
                last[1] = Math.max(last[1], cur[1]);
            } else {
                res.add(cur);
            }
        }
        return res.toArray(new int[0][0]);
    }
}
