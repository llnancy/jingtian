package com.sunchaser.sparrow.algorithm.leetcode.easy;

/**
 * 121. 买卖股票的最佳时机
 * <p>
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/15
 */
public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(prices));
    }

    public static int maxProfit(int[] prices) {
        int n = prices.length;
        // 一维：今天是第几天
        // 二维：最大限制交易次数=1次
        // 三维：0：今天没有持有股票；1：今天持有股票
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 防止第一天索引越界
            if (i - 1 == -1) {
                // 今天没有持有股票，利润为0。
                dp[i][0] = 0;
                // 今天持有股票，表示今天刚买入，利润为-prices[i]
                dp[i][1] = -prices[i];
                continue;
            }
            // 今天没有持有股票：
            // 情况一：昨天没有持有股票，今天rset
            // 情况二：昨天持有股票，今天sell卖出，利润需要加上prices[i]
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 今天持有股票：
            // 情况一：昨天就持有股票，今天rset
            // 情况二：昨天没有持有股票，今日buy买入，利润需要减去prices[i]
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        // 最后一天没有持有股票利润最大（持有的股票都卖出去了）
        return dp[n - 1][0];
    }
}
