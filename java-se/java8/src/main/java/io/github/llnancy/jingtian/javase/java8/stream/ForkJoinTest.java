package io.github.llnancy.jingtian.javase.java8.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * test fork/join
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/14
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        SumTask task = new SumTask(1, 100);
        Long sum = forkJoinPool.invoke(task);
        System.out.println("sum = " + sum);
    }
}

class SumTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 100;

    private final int start;

    private final int end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 小于阈值，不用再拆分，进行计算
            long sum = 0L;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 需要拆分
            int middle = (start + end) >> 1;
            SumTask leftTask = new SumTask(start, middle);
            SumTask rightTask = new SumTask(middle + 1, end);
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();
        }
    }
}
