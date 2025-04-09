package io.github.llnancy.jingtian.algorithm.leetcode.easy;

/**
 * 70. 爬楼梯
 * <a href="https://leetcode-cn.com/problems/climbing-stairs/">https://leetcode-cn.com/problems/climbing-stairs/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class ClimbingStairs {

    /**
     * 斐波那契数列问题，使用滚动数组思想优化空间复杂度。
     */

    public int climbStairs(int n) {
        int p = 0;
        int q = 0;
        int r = 1;
        for (int i = 1; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }
}
