package io.github.llnancy.algorithm.leetcode.hot100;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * <a href="https://leetcode-cn.com/problems/two-sum/">https://leetcode-cn.com/problems/two-sum/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/12/17
 */
@Slf4j
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9};
        // int[] twoSum = twoSum1(nums, 10);
        int[] twoSum = twoSum2(nums, 10);
        LOGGER.info("result: {}", twoSum);
    }

    /**
     * 枚举数组中的每一个数 x，寻找数组中是否存在 target - x
     * 遍历时，每一个位于 x 之前的元素都已经和 x 匹配过，所以只需要在 x 后面的元素中寻找 target - x。
     */
    public static int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[] {-1, -1};
    }

    /**
     * 使用哈希表，对于每一个 x，先查询哈希表中是否存在 target - x，如果存在则找到结果；如果不存在则将 x 存入哈希表。
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> valToIndex = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (valToIndex.containsKey(target - nums[i])) {
                return new int[]{valToIndex.get(target - nums[i]), i};
            } else {
                valToIndex.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }
}
