package com.sunchaser.sparrow.algorithm.leetcode.hot100;

/**
 * 最长回文子串
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/6
 */
public class LongestPalindromicSubstring {
    public static String longestPalindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 以s[i]为中心的最长回文子串
            String r1 = palindrome(s, i, i);
            // 以s[i]和s[i + 1]为中心的最长回文子串
            String r2 = palindrome(s, i, i + 1);
            // res = longest(res, r1, r2)
            res = res.length() > r1.length() ? res : r1;
            res = res.length() > r2.length() ? res : r2;
        }
        return res;
    }

    private static String palindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            // 从中间向两边扩散寻找回文子串
            left--;
            right++;
        }
        // 截取 [left + 1, right) 左闭右开
        return s.substring(left + 1, right);
    }
}
