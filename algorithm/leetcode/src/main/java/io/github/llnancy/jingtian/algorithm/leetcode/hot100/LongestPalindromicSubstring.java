package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 最长回文子串
 * <a href="https://leetcode-cn.com/problems/longest-palindromic-substring/">https://leetcode-cn.com/problems/longest-palindromic-substring/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/6
 */
public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 以 s[i] 为中心的最长回文子串
            String r1 = palindrome(s, i, i);
            // 以 s[i] 和 s[i + 1] 为中心的最长回文子串
            String r2 = palindrome(s, i, i + 1);
            // res = longest(res, r1, r2)
            res = res.length() > r1.length() ? res : r1;
            res = res.length() > r2.length() ? res : r2;
        }
        return res;
    }

    private String palindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            // 从中间向两边扩散寻找回文子串
            left--;
            right++;
        }
        // 从 left + 1 到 right - 1 的字符串为回文串
        return s.substring(left + 1, right);
    }
}
