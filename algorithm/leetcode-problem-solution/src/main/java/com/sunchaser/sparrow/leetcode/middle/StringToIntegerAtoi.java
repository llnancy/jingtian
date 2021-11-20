package com.sunchaser.sparrow.leetcode.middle;

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

    public static int atoi2(String s) {
        int len = s.length();
        char[] cArr = s.toCharArray();
        int res = 0;
        int index = 0;
        // 去除前导空格
        while (index < len && cArr[index] == ' ') {
            index++;
        }
        // 极端情况 '    '
        if (index == len) {
            return res;
        }
        int sign = 1;
        char firstChar = cArr[index];
        if (firstChar == '+') {
            index++;
        } else if (firstChar == '-') {
            sign = -1;
            index++;
        }
        while (index < len) {
            char curChar = cArr[index];
            if (curChar < '0' || curChar > '9') {
                break;
            }
            // 越界判断
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && (curChar - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && (curChar - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }

            res = res * 10 + sign * (curChar - '0');
            index++;
        }
        return res;
    }

}
