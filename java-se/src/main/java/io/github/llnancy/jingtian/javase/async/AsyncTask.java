package io.github.llnancy.jingtian.javase.async;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 异步任务工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/24
 */
@Slf4j
public class AsyncTask {
    private AsyncTask() {
    }

    /**
     * 执行多个IAsyncConsumer任务并调用get方法确保执行成功
     *
     * @param params        任务参数集合
     * @param asyncConsumer 任务
     * @param <P>           param 参数
     */
    public static <P> void asyncAccepts(List<P> params, IAsyncConsumer<P> asyncConsumer) {
        List<Future<Void>> futureList = params.stream()
                .map(asyncConsumer::accept)
                .collect(Collectors.toList());
        for (Future<Void> future : futureList) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("AsyncTask#asyncAccepts error", e);
                throw new AsyncRuntimeException(e);
            }
        }
    }

    /**
     * 执行多个IAsyncFunction任务，并收集其执行结果
     *
     * @param params        任务参数集合
     * @param asyncFunction 任务
     * @param <P>           param 参数
     * @param <R>           return 返回值
     * @return 所有任务返回值的集合
     */
    public static <P, R> List<R> asyncApplies(List<P> params, IAsyncFunction<P, R> asyncFunction) {
        List<Future<R>> futureList = params.stream()
                .map(asyncFunction::apply)
                .collect(Collectors.toList());
        List<R> resultList = Lists.newArrayListWithExpectedSize(params.size());
        for (Future<R> future : futureList) {
            try {
                resultList.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("AsyncTask#asyncApplies error", e);
                throw new AsyncRuntimeException(e);
            }
        }
        return resultList;
    }

    public static class AsyncRuntimeException extends RuntimeException {

        private static final long serialVersionUID = -4615255664865122399L;

        /**
         * Constructs a new runtime exception with the specified cause and a
         * detail message of {@code (cause==null ? null : cause.toString())}
         * (which typically contains the class and detail message of
         * {@code cause}).  This constructor is useful for runtime exceptions
         * that are little more than wrappers for other throwables.
         *
         * @param cause the cause (which is saved for later retrieval by the
         *              {@link #getCause()} method).  (A {@code null} value is
         *              permitted, and indicates that the cause is nonexistent or
         *              unknown.)
         * @since 1.4
         */
        public AsyncRuntimeException(Throwable cause) {
            super(cause);
        }
    }
}
