package com.sunchaser.sparrow.java8.chapter12;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author sunchaser
 * @date 2019/8/29
 * @description 传统时间格式转换器线程安全问题演示
 */
public class TraditionalSimpleDateFormat {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 使用线程池模拟多线程
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 定义解析日期字符串任务：使用SimpleDateFormat对象解析
        Callable<Date> task = () -> sdf.parse("2019-8-29");
        List<Future<Date>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 执行得到解析结果
            result.add(threadPool.submit(task));
        }
//        result.forEach(f -> {
//            try {
//                System.out.println(f.get());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        for (Future<Date> r : result) {
            System.out.println(r.get());
        }
        threadPool.shutdown();
    }
}
