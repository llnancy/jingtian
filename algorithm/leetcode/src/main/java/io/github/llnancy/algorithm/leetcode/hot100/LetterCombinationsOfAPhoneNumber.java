package io.github.llnancy.algorithm.leetcode.hot100;

import java.util.LinkedList;
import java.util.List;

/**
 * 电话号码的字母组合
 * <a href="https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/17
 */
public class LetterCombinationsOfAPhoneNumber {

    /*
    全排列问题，回溯算法
     */

    /**
     * 数字到字母的映射：数组下标为数字，数组内容为字母。
     */
    private final String[] mapping = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    /**
     * 结果集
     */
    private final List<String> res = new LinkedList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return res;
        }
        // 从 digits[0] 开始回溯
        backtrack(digits, 0, new StringBuilder());
        return res;
    }

    private void backtrack(String digits, int start, StringBuilder builder) {
        if (builder.length() == digits.length()) {
            // 到达回溯树底部
            res.add(builder.toString());
            return;
        }
        for (int i = start; i < digits.length(); i++) {
            // 得到 i 位置对应的数字：字符减 '0' 得到对应数字
            // 计算机中字符以 ASCII 码形式存储，字符 '0' 的 ASCII 码是 48，字符相减实际是 ASCII 码值相减，常用于将字符型数字转为整数
            int digit = digits.charAt(i) - '0';
            for (char c : mapping[digit].toCharArray()) {
                // 做选择
                builder.append(c);
                // 进入下一层回溯树
                backtrack(digits, i + 1, builder);
                // 撤销选择
                builder.deleteCharAt(builder.length() - 1);
            }
        }
    }
}
