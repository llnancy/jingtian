package io.github.llnancy.jingtian.javase.async;

import java.util.concurrent.Future;

/**
 * 异步函数型函数式接口
 * @param <P> param 参数
 * @param <R> return 返回值
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/24
 */
@FunctionalInterface
public interface IAsyncFunction<P, R> {
    Future<R> apply(P param);
}
