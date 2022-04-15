package com.sunchaser.sparrow.java8.chapter7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 拆分合并计算任务：求和start到end之间所有数的和。
 * ForkJoinTask类有两个子类：
 * RecursiveAction：用于没有返回结果的任务
 * RecursiveTask：用于有返回结果的任务
 * @author sunchaser
 * @since JDK8 2019/8/22
 */
public class ForkJoinCountTask extends RecursiveTask<Integer> {
    /**
     * 阈值
     */
    private static final int THRESHOLD = 2;

    private int start;
    private int end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * ForkJoinTask与一般任务的主要区别在于它需要实现compute方法，
     * 在这个方法里，首先需要判断任务是否足够小，如果足够小就直接执行任务。
     * 如果不足够小，就必须分割成两个子任务，每个子任务在调用fork方法时，又会进入 compute方法，
     * 看看当前子任务是否需要继续分割成子任务，如果不需要继续分割，则执行当前子任务并返回结果。
     * 使用join方法会等待子任务执行完并得到其结果。
     * @return
     */
    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) <= THRESHOLD) {
            // 任务达到阈值，不需再拆分，顺序执行
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 任务拆分
            int middle = (start + end) >> 1;
            ForkJoinCountTask leftTask = new ForkJoinCountTask(start,middle);
            ForkJoinCountTask rightTask = new ForkJoinCountTask(middle + 1,end);
            // fork
            leftTask.fork();
            rightTask.fork();
            // join
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    // for test
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCountTask countTask = new ForkJoinCountTask(1,100);
        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * isCompletedAbnormally()：检查任务是否已经抛出异常或已经被取消了，可以通过ForkJoinTask的getException方法获取异常。
         * getException()：返回Throwable对象，如果任务被取消了则返回CancellationException。如果任务没有完成或者没有抛出异常则返回null。
         */
        if (countTask.isCompletedAbnormally()) {
            System.out.println(countTask.getException());
        }
    }
}
