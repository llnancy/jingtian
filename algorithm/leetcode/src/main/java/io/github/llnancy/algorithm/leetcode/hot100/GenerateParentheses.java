package io.github.llnancy.algorithm.leetcode.hot100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 括号生成
 * <a href="https://leetcode.cn/problems/generate-parentheses/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/generate-parentheses/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/24
 */
public class GenerateParentheses {

    /*
    回溯算法。用 left 记录还可以使用多少个左括号；用 right 记录还可以使用多少个右括号。
    从左往右，左括号数量一定大于等于右括号数量，即如果还可以使用的左括号比右括号多，就不合法。
     */

    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        List<String> res = new ArrayList<>();
        // 可用的左括号和右括号数量初始为 n
        backtrack(n, n, new StringBuilder(), res);
        return res;
    }

    private void backtrack(int left, int right, StringBuilder track, List<String> res) {
        if (right < left) {
            // 还可以使用的左括号比右括号多，不合法
            return;
        }
        if (left < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            // 左括号和右括号恰好用完时，得到一个合法的括号组合
            res.add(track.toString());
            return;
        }

        // 做选择：放一个左括号
        track.append('(');
        backtrack(left - 1, right, track, res);
        // 撤销选择
        track.deleteCharAt(track.length() - 1);

        // 做选择：放一个右括号
        track.append(')');
        backtrack(left, right - 1, track, res);
        // 撤销选择
        track.deleteCharAt(track.length() - 1);
    }
}
