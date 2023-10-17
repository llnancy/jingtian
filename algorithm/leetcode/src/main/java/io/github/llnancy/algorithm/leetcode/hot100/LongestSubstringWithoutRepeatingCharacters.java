package io.github.llnancy.algorithm.leetcode.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * 无重复字符的最长子串
 * <a href="https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/">https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/6
 */
public class LongestSubstringWithoutRepeatingCharacters {

    public int lengthOfLongestSubstring(String s) {
        // 定义窗口
        Map<Character, Integer> window = new HashMap<>(s.length());
        // 定义滑动变量和结果
        int left = 0, right = 0, res = 0;
        while (right < s.length()) {
            // 窗口右移
            char c = s.charAt(right++);
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 有重复字符的情况，窗口从左侧缩小。
            while (window.get(c) > 1) {
                char d = s.charAt(left++);
                window.put(d, window.getOrDefault(d, 1) - 1);
            }
            // 更新结果
            res = Math.max(res, right - left);
        }
        return res;
    }
}
