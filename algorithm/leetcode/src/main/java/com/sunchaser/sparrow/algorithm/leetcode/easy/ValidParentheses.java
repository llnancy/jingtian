package com.sunchaser.sparrow.algorithm.leetcode.easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 20. 有效的括号
 * <p>
 * https://leetcode-cn.com/problems/valid-parentheses/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class ValidParentheses {
    public static boolean isValid(String s) {
        char[] chars = s.toCharArray();
        Deque<Character> stack = new LinkedList<>();
        for (char c : chars) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                Character temp = null;
                if (c == ')') {
                    temp = '(';
                } else if (c == '}') {
                    temp = '{';
                } else if (c == ']') {
                    temp = '[';
                }
                if (temp == stack.peek()) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
