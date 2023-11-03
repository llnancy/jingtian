package io.github.llnancy.jingtian.algorithm.leetcode.medium;

/**
 * 8. 字符串转换整数 (atoi)
 * <p>
 * https://leetcode-cn.com/problems/string-to-integer-atoi/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class StringToIntegerAtoi {

    public static void main(String[] args) {
        System.out.println(atoi("-91283472332"));
    }

    public static int atoi(String s) {
        s = s.trim();
        char[] cArr = s.toCharArray();
        boolean isVisitFuHao = false;
        boolean isVisitNum = false;
        boolean flag = true;
        int res = 0;
        int last = 0;
        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];
            if (c == '+' || c == '-') {
                if (isVisitFuHao) {
                    return flag ? res : -res;
                }
                if (isVisitNum) {
                    return res;
                }
                flag = c == '+';
                isVisitFuHao = true;
                continue;
            }
            if (res == 0 && c == '0') {
                isVisitNum = true;
                continue;
            }
            if (c < '0' || c > '9') {
                break;
            }
            isVisitNum = true;
            last = res;
            res = res * 10 + (c - '0');
            if (res / 10 != last) {
                return flag ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
        }
        if (flag) {
            if (res < 0) {
                res = Integer.MAX_VALUE;
            }
        } else {
            res = -res;
            if (res > 0) {
                res = Integer.MIN_VALUE;
            }
        }
        return res;
    }

    /**
     * 1 去除前导空格，并防止全部为空格的情况
     * 2 记录第一个字符的正负号
     * 3 循环处理后续字符
     * 3.1 如果遇到非0-9的字符则立即退出
     * 3.2 如果大于Integer.MAX_VALUE则返回Integer.MAX_VALUE
     * 3.3 如果小于Integer.MIN_VALUE则返回Integer.MIN_VALUE
     * 3.4 累加结果
     */
    public static int atoi2(String s) {
        int n = s.length();
        char[] sArr = s.toCharArray();
        int res = 0;
        int cur = 0;
        // 1 去除前导空格
        while (cur < n && sArr[cur] == ' ') {
            cur++;
        }
        // 1.1 防止全部为空格的情况
        if (cur == n) {
            return res;
        }
        // 2 记录第一个字符的正负号
        int flag = 1;
        if (sArr[cur] == '+') {
            cur++;
        } else if (sArr[cur] == '-') {
            cur++;
            flag = -1;
        }
        // 3 循环处理后续字符
        while (cur < n) {
            // 3.1 遇到非0-9字符则退出
            if (sArr[cur] < '0' || sArr[cur] > '9') {
                break;
            }
            // 3.2 溢出返回
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && sArr[cur] - '0' > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            // 3.3 溢出返回
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && sArr[cur] - '0' > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }
            // 计算结果
            res = res * 10 + flag * (sArr[cur] - '0');
            cur++;
        }
        return res;
    }

}
