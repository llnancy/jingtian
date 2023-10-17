package io.github.llnancy.jingtian.javase.java8.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * test {@link java.util.concurrent.CompletableFuture}
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/7/17
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        // 执行异步操作
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 42);
        CompletableFuture<String> apply = future.thenApply(result -> "Result: " + result);
        apply.thenAccept(System.out::println);

        // 等待异步操作完成
        apply.join();

        // 链式调用
        CompletableFuture.supplyAsync(() -> 42)
                .thenApply(result -> "Result: " + result)
                .thenAccept(System.out::println)
                .join();
    }
}
