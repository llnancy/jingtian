package com.sunchaser.sparrow.leetcode.middle;

import java.util.HashMap;
import java.util.Map;

/**
 * 3. 无重复字符的最长子串
 *
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * 滑动窗口
     */
    public static int lengthOfLongestSubstring(String s) {
        char[] sArr = s.toCharArray();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        int res = 0;
        while (right < sArr.length) {
            char c = sArr[right++];
            window.put(c, window.getOrDefault(c, 0) + 1);

            while (window.get(c) > 1) {
                char d = sArr[left++];
                window.put(d, window.getOrDefault(d, 1) - 1);
            }
            res = Math.max(res, right - left);
        }
        return res;
    }
}
