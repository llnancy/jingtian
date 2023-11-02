package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 56. 合并区间
 * <p>
 * https://leetcode-cn.com/problems/merge-intervals/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        LinkedList<int[]> res = new LinkedList<>();
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            int[] last = res.getLast();
            if (interval[0] <= last[1]) {
                last[1] = Math.max(last[1], interval[1]);
            } else {
                res.add(interval);
            }
        }
        return res.toArray(new int[0][0]);
    }
}
