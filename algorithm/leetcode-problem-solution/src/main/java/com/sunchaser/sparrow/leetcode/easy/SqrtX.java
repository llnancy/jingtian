package com.sunchaser.sparrow.leetcode.easy;

/**
 * 69. Sqrt(x)
 * <p>
 * https://leetcode-cn.com/problems/sqrtx/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class SqrtX {

    public static int sqrt(int n) {
        int left = 0;
        int right = n;
        int mid;
        while (left <= right) {
            mid = (left + right) >>> 1;
            long i = (long) mid * mid;
            long j = (long) (mid + 1) * (long) (mid + 1);
            if (i == n || (i < n && j > n)) {
                return mid;
            } else if (i > n) {
                right = mid - 1;
            } else if (i < n) {
                left = mid + 1;
            }
        }
        return -1;
    }
}
