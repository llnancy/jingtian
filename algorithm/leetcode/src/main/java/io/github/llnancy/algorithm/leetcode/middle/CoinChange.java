package io.github.llnancy.algorithm.leetcode.middle;

import java.util.Arrays;

/**
 * 322. 零钱兑换
 * <p>
 * https://leetcode-cn.com/problems/coin-change/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/1
 */
public class CoinChange {

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        int coinChange = coinChange(coins, amount);
        int coinChangeUseMemo = coinChangeUseMemo(coins, amount);
        System.out.println(coinChange);
        System.out.println(coinChangeUseMemo);
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 子问题的解：subProblem个硬币凑成amount - coin元
            int subProblem = coinChange(coins, amount - coin);
            // 子问题无解跳过
            if (subProblem == -1) continue;
            // 子问题的最优解
            res = Math.min(res, subProblem + 1);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * 备忘录解法，每次将子问题的解存入备忘录
     */
    public static int coinChangeUseMemo(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -999);
        return dp(coins, amount, memo);
    }

    private static int dp(int[] coins, int amount, int[] memo) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        // 备忘录中存在则直接返回
        if (memo[amount] != -999) {
            return memo[amount];
        }
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subProblem = dp(coins, amount - coin, memo);
            if (subProblem == -1) continue;
            res = Math.min(res, subProblem + 1);
        }
        // 结果存入备忘录
        memo[amount] = (res == Integer.MAX_VALUE) ? -1 : res;
        return memo[amount];
    }

    /**
     * dp数组的定义：当目标金额为i时，至少需要dp[i]枚硬币凑出。
     */
    public static int coinChange2(int[] coins, int amount) {
        // 定义dp数组并初始化
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        // base case
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int coin : coins) {
                // 子问题无解跳过
                if (i - coin < 0) continue;
                // 求最小值（最少硬币数）
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
