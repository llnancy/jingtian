package com.sunchaser.java8.chapter12;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author sunchaser
 * @date 2019/8/29
 * @description 传统时间格式转换器线程安全实现演示
 */
public class TraditionalThreadSafeSimpleDateFormat {
    public static void main(String[] args) throws Exception {
        // 使用线程池模拟多线程
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 定义解析日期字符串任务：每个任务都有一份SimpleDateFormat对象的副本
        Callable<Date> task = () -> TraditionalSimpleDateFormatThreadLocal.convert("2019-8-29");
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
