package com.sunchaser.sparrow.java8.chapter12;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Java 8新的日期时间转换器，不可变，线程安全。
 * @author sunchaser
 * @since JDK8 2019/8/29
 */
public class NewDateTimeFormatter {
    public static void main(String[] args) throws Exception {
        // 按照哪种格式进行格式转换
        // DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        // LocalDate.parse("20190829",dtf)：将第一个字符串参数按照第二个参数定义的格式器解析，返回一个LocalDate对象
        Callable<LocalDate> task = () -> LocalDate.parse("20190829",dtf);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(threadPool.submit(task));
        }
//        result.forEach(f -> {
//            try {
//                System.out.println(f.get());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        for (Future<LocalDate> r : result) {
            System.out.println(r.get());
        }
        threadPool.shutdown();
    }
}
