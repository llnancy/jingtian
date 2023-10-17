package io.github.llnancy.jingtian.javase.java12;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * {@link java.text.NumberFormat}
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK12 2023/7/17
 */
public class NumberFormatTest {

    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        System.out.println(nf.format(1000)); // 1K
        System.out.println(nf.format(10000)); // 10K
        System.out.println(nf.format(100000)); // 100K
        System.out.println(nf.format(1000000)); // 1M

        // 设置小数位数
        nf.setMaximumFractionDigits(1);
        System.out.println(nf.format(1234)); // 1.2K
        System.out.println(nf.format(12345)); // 12.3K
        System.out.println(nf.format(123456)); // 123.5K
        System.out.println(nf.format(1234567)); // 1.2M
        System.out.println(nf.format(12345678)); // 12.3M
    }
}
