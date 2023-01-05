package com.sunchaser.sparrow.javase.concurrency;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 锁消除
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/28
 */
@BenchmarkMode(Mode.AverageTime) // 统计模式
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 统计单位
@Warmup(iterations = 3) // 预热 3 轮
@Measurement(iterations = 5) // 度量 5 轮
@Fork(1)
public class EliminateLocks {

    private static int x = 0;

    @Benchmark
    public void increment() {
        x++;
    }

    @Benchmark
    public void syncIncrement() {
        // 这里的 obj 是局部变量，不会被共享，JIT 做热点代码优化时会进行锁消除。
        Object obj = new Object();
        // 可使用 VM 参数 -XX:-EliminateLocks 禁用锁消除
        synchronized (obj) {
            x++;
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(EliminateLocks.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
