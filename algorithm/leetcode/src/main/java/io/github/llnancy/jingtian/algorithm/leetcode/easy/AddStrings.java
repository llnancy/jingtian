package io.github.llnancy.jingtian.algorithm.leetcode.easy;

/**
 * 415. 字符串相加
 * 链接：https://leetcode-cn.com/problems/add-strings
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class AddStrings {

    public static void main(String[] args) {
        System.out.println(addStrings("123", "12345"));
    }

    public static String addStrings2(String num1, String num2) {
        char[] c1 = num1.toCharArray();
        char[] c2 = num2.toCharArray();
        int jin = 0;
        StringBuilder res = new StringBuilder();
        int i = c1.length - 1;
        int j = c2.length - 1;
        while (i >= 0 && j >= 0) {
            int sum = Character.getNumericValue(c1[i--]) + Character.getNumericValue(c2[j--]) + jin;
            if (sum >= 10) {
                jin = sum / 10;
                res.append(sum % 10);
            } else {
                jin = 0;
                res.append(sum);
            }
        }
        if (i < 0) {
            while (j >= 0) {
                int sum = Character.getNumericValue(c2[j--]) + jin;
                if (sum >= 10) {
                    jin = sum / 10;
                    res.append(sum % 10);
                } else {
                    jin = 0;
                    res.append(sum);
                }
            }
        } else {
            while (i >= 0) {
                int sum = Character.getNumericValue(c1[i--]) + jin;
                if (sum >= 10) {
                    jin = sum / 10;
                    res.append(sum % 10);
                } else {
                    jin = 0;
                    res.append(sum);
                }
            }
        }
        if (jin != 0) {
            res.append(jin);
        }
        return res.reverse().toString();
    }

    public static String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int jin = 0;
        StringBuilder res = new StringBuilder();
        while (i >= 0 || j >= 0 || jin != 0) {
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;
            int sum = x + y + jin;
            if (sum >= 10) {
                jin = sum / 10;
                res.append(sum % 10);
            } else {
                jin = 0;
                res.append(sum);
            }
            i--;
            j--;
        }
        return res.reverse().toString();
    }
}
