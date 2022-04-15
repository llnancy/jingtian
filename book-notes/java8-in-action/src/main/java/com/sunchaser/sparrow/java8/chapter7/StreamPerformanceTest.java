package com.sunchaser.sparrow.java8.chapter7;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 流性能测试
 * @author sunchaser
 * @since JDK8 2019/8/21
 */
public class StreamPerformanceTest {
    /**
     * 传统迭代方式求和
     * @param n
     * @return
     */
    public static long iterateSum(long n) {
        long result = 0;
        for (int i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    /**
     * 串行流方式求和
     * @param n
     * @return
     */
    public static long streamSum(long n) {
        Long result = Stream.iterate(0L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
        return result;
    }

    /**
     * LongStream串行流方式求和，避免装箱操作。
     * @param n
     * @return
     */
    public static long longStreamSum(long n) {
        long result = LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
        return result;
    }

    /**
     * 并行流方式求和
     * @param n
     * @return
     */
    public static long parallelSum(long n) {
        Long result = Stream.iterate(0L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
        return result;
    }

    /**
     * LongStream并行流方式求和
     * @param n
     * @return
     */
    public static long longParallelSum(long n) {
        long result = LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
        return result;
    }

    /**
     * 求10次累计操作耗时的平均值
     * @param adder
     * @param n
     * @return
     */
    public static long measureSumPerf(Function<Long,Long> adder,long n) {
        long fastest = Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_100_100;
            System.out.println("result: " + sum);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }

    // test
    public static void main(String[] args) {
        // 原始迭代方式更快，它不需要对原始类型进行装箱/拆箱操作
        System.out.println("iterate sum min time:" + measureSumPerf(StreamPerformanceTest::iterateSum, 100000000));
        // iterate生成的是装箱的对象，必须拆箱成数字才能求和。
        System.out.println("stream sum min time:" + measureSumPerf(StreamPerformanceTest::streamSum, 100000000));
        // 整个数字在过程开始时并没有准备好，无法有效的把流划分为小块来并行处理。
        System.out.println("parallel stream sum min time:" + measureSumPerf(StreamPerformanceTest::parallelSum, 100000000));
        // LongStream.rangeClosed直接产生原始类型的long数字，没有装箱拆箱的开销。
        System.out.println("long stream sum min time:" + measureSumPerf(StreamPerformanceTest::longStreamSum,100000000));
        // LongStream.rangeClosed在过程开始时就会生成数字范围，很容易拆分为独立的小块进行并行处理。
        System.out.println("long parallel stream sum min time:" + measureSumPerf(StreamPerformanceTest::longParallelSum,100000000));
    }
}
