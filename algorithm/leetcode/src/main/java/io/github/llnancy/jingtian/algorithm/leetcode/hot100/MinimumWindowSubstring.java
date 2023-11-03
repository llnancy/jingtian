package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * 最小覆盖子串
 * <a href="https://leetcode.cn/problems/minimum-window-substring/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/minimum-window-substring/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/3
 */
public class MinimumWindowSubstring {

    /*
    滑动窗口算法。
     */

    public String minWindow(String s, String t) {
        // 记录需要凑齐的字符和对应个数
        Map<Character, Integer> need = new HashMap<>();
        // 记录窗口内的字符和对应个数
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 定义窗口边界
        int left = 0;
        int right = 0;

        // 定义已凑齐的字符个数
        int valid = 0;
        // 定义最小覆盖子串开始位置
        int start = 0;
        // 定义最小覆盖子串长度
        int len = Integer.MAX_VALUE;

        while (right < s.length()) {

            // 窗口右移。c 是移入窗口的元素。
            char c = s.charAt(right++);

            if (need.containsKey(c)) {
                // c 是要凑齐的字符
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    // 窗口内字符 c 的个数和需要的个数相等时，已凑齐字符个数加一。
                    valid++;
                }
            }

            // 凑齐了所有字符，开始从左边缩小窗口
            while (valid == need.size()) {

                // 更新最小覆盖子串位置
                if (right - left < len) {
                    start = len;
                    len = right - left;
                }

                // 窗口左侧收缩。d 是移出窗口的元素。
                char d = s.charAt(left++);

                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        // d 被移出窗口，如果个数相等，则已凑齐字符个数减一。
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 1) - 1);
                }
            }
        }
        // 返回最小覆盖子串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}
