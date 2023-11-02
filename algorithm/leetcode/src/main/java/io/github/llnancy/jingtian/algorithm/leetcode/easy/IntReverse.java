package io.github.llnancy.jingtian.algorithm.leetcode.easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * //给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * //
 * // 示例 1:
 * //
 * // 输入: 123
 * //输出: 321
 * //
 * //
 * // 示例 2:
 * //
 * // 输入: -123
 * //输出: -321
 * //
 * //
 * // 示例 3:
 * //
 * // 输入: 120
 * //输出: 21
 * //
 * //
 * // 注意:
 * //
 * // 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231, 231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 * // Related Topics 数学
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/1
 */
public class IntReverse {
    public static int convertStringToReverse(int n) {
        boolean flag = n < 0;
        String s = String.valueOf(Math.abs(n));
        char[] chars = s.toCharArray();
        Deque<Character> stack = new LinkedList<>();
        for (char aChar : chars) {
            stack.push(aChar);
        }
        StringBuilder res = new StringBuilder();
        if (flag) {
            res.append("-");
        }
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        try {
            return Integer.parseInt(res.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static int reverse(int n) {
        int res = 0;
        while (n != 0) {
            int x = n % 10;
            if ((res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && n > Integer.MAX_VALUE % 10))
                    || (res < Integer.MIN_VALUE / 10 || res == Integer.MIN_VALUE / 10 && n < Integer.MIN_VALUE % 10)) {
                return 0;
            }
            res = res * 10 + x;
            n /= 10;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(convertStringToReverse(2147483647));
        System.out.println(reverse(2143847412));
        System.out.println(reverse(2147483412));
    }
}
