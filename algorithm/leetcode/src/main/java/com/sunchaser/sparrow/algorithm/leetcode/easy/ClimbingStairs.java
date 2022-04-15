package com.sunchaser.sparrow.algorithm.leetcode.easy;

/**
 * 70. 爬楼梯
 * <p>
 * https://leetcode-cn.com/problems/climbing-stairs/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class ClimbingStairs {
    public static int climbStairs(int n) {
        if (n < 3) {
            return n;
        }
        int p = 1;
        int q = 2;
        int sum = 0;
        for (int i = 3;i < n;i++) {
            sum = p + q;
            p = q;
            q = sum;
        }
        return sum;
    }
}
