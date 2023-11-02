package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.HashSet;
import java.util.Set;

/**
 * 最长连续序列
 * <a href="https://leetcode.cn/problems/longest-consecutive-sequence/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/longest-consecutive-sequence/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/2
 */
public class LongestConsecutiveSequence {

    /*
    利用空间换时间，先将数组元素放置在哈希表中，然后寻找连续序列的第一个元素，找到第一个元素后依次向后从哈希表中查找下一个连续元素。
     */

    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int res = 0;
        for (int num : set) {
            if (set.contains(num - 1)) {
                // num 不是连续序列的第一个，跳过
                continue;
            }
            // num 是连续序列的第一个，开始向后计算连续序列的长度
            int curNum = num;
            int curLen = 1;
            while (set.contains(curNum + 1)) {
                curNum += 1;
                curLen += 1;
            }
            res = Math.max(res, curLen);
        }
        return res;
    }
}
