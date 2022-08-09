package com.sunchaser.sparrow.algorithm.leetcode.hot100;

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

    public static int lengthOfLongestSubstring(String s) {
        char[] sArr = s.toCharArray();
        // 定义窗口
        Map<Character, Integer> window = new HashMap<>(sArr.length);
        int left = 0, right = 0, res = 0;
        while (right < sArr.length) {
            // 右侧移入窗口
            char c = sArr[right++];
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 遇到重复字符，从左侧缩小窗口
            while (window.get(c) > 1) {
                char d = sArr[left++];
                window.put(d, window.getOrDefault(d, 1) - 1);
            }
            // 更新结果
            res = Math.max(res, right - left);
        }
        return res;
    }
}
