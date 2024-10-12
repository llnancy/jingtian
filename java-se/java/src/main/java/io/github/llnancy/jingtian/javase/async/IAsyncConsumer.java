package io.github.llnancy.jingtian.javase.async;

import java.util.concurrent.Future;

/**
 * 异步消费型函数式接口
 *
 * @param <P> param 参数
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/24
 */
@FunctionalInterface
public interface IAsyncConsumer<P> {

    Future<Void> accept(P param);
}
