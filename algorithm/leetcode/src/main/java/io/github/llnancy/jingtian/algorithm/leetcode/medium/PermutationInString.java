package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串的排列
 * <a href="https://leetcode.cn/problems/permutation-in-string/description/">https://leetcode.cn/problems/permutation-in-string/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/3
 */
public class PermutationInString {

    /*
    滑动窗口算法
     */

    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : s1.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0;
        int right = 0;
        int valid = 0;
        while (right < s2.length()) {
            char c = s2.charAt(right++);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            while (right - left >= s1.length()) {
                if (valid == need.size()) {
                    return true;
                }
                char d = s2.charAt(left++);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 1) - 1);
                }
            }
        }
        return false;
    }
}
