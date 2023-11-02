package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 有效的括号
 * <a href="https://leetcode.cn/problems/valid-parentheses/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/valid-parentheses/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/24
 */
public class ValidParentheses {

    /*
    利用栈先进后出的特性。遇到左括号则压入栈中，遇到右括号则比较栈顶元素是否为对应左括号。
     */

    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
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
