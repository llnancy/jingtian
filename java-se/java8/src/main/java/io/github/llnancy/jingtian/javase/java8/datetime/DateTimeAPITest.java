package io.github.llnancy.jingtian.javase.java8.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * test date-time API
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/17
 */
public class DateTimeAPITest {

    public static void main(String[] args) {
        // 创建日期
        LocalDate currentDate = LocalDate.now();
        System.out.println("当前日期：" + currentDate);

        // 格式化日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        System.out.println("格式化日期：" + formattedDate);

        // 解析日期字符串
        String dateString = "2023-07-17";
        LocalDate parsedDate = LocalDate.parse(dateString, formatter);
        System.out.println("解析日期字符串：" + parsedDate);

        // 创建日期和时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("当前日期和时间：" + currentDateTime);
    }
}
