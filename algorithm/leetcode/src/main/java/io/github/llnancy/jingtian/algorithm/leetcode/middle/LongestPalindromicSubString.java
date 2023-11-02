package io.github.llnancy.jingtian.algorithm.leetcode.middle;

/**
 * 5. 最长回文子串
 * <p>
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class LongestPalindromicSubString {

    public static void main(String[] args) {
        String s = "babad";
        System.out.println(longestPalindrome(s));
    }

    public static String longestPalindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 以s[i]为中心的最长回文子串
            String s1 = palindrome(s, i, i);
            // 以s[i]和s[i + 1]为中心的最长回文子串
            String s2 = palindrome(s, i, i + 1);
            // res = longest(res, s1, s2)
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res;
    }

    private static String palindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            // 向两边展开
            left--;
            right++;
        }
        // 截取 [left + 1, right) 左闭右开
        return s.substring(left + 1, right);
    }
}
