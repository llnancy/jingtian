package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 每日温度
 * <a href="https://leetcode.cn/problems/daily-temperatures/?envType=problem-list-v2&envId=2cktkvj">...</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/5/10
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        int[] temperatures = {73,74,75,71,69,72,76,73};
        DailyTemperatures d = new DailyTemperatures();
        int[] ints = d.dailyTemperatures(temperatures);
        System.out.println(ints);
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            int cur = temperatures[i];
            for (int j = i + 1; j < temperatures.length; j++) {
                if (temperatures[j] > cur) {
                    res[i] = j - i;
                    break;
                }
            }
        }
        return res;
    }
}
