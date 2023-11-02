package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 最长有效括号
 * <a href="https://leetcode.cn/problems/longest-valid-parentheses/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/longest-valid-parentheses/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/25
 */
public class LongestValidParentheses {

    public int longestValidParentheses(String s) {
        Deque<Integer> stack = new LinkedList<>();
        // dp[i] 的定义：记录以 s[i - 1] 结尾的最长合法括号子串长度
        int[] dp = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                // 遇到左括号，记录索引
                stack.push(i);
                // 左括号不可能是合法括号子串的结尾
                dp[i + 1] = 0;
            } else {
                // 遇到右括号
                if (!stack.isEmpty()) {
                    // 配对的左括号对应索引
                    Integer leftIndex = stack.pop();
                    int len = i + 1 - leftIndex + dp[leftIndex];
                    dp[i + 1] = len;
                } else {
                    // 没有匹配的左括号
                    dp[i + 1] = 0;
                }
            }
        }
        // 计算最长子串长度
        int res = 0;
        for (int j : dp) {
            res = Math.max(res, j);
        }
        return res;
    }
}
