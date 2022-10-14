# `Java` 线程的状态

`Java` 线程的生命周期中一共存在六种状态，在 `java.lang.Thread.State` 枚举中进行了定义。

```java
public enum State {

    NEW, // 新创建

    RUNNABLE, // 可运行

    BLOCKED, // 被阻塞

    WAITING, // 等待

    TIMED_WAITING, // 计时等待

    TERMINATED; // 被终止
}
```

> 我们可以通过 `java.lang.Thread#getState` 方法获取线程的当前状态。任意时刻，一个线程只能处于一种状态。

下面用有限自动状态机的概念来看这六个状态，每一个状态的扭转都代表着相应事件的触发，下面是状态转移表和状态转化图。

| 条件\当前状态                                   | `NEW`      | `RUNNABLE`      | `BLOCKED`  | `WAITING`  | `TIMED_WAITING` | `TERMINATED` |
| ----------------------------------------------- | ---------- | --------------- | ---------- | ---------- | --------------- | ------------ |
| `Thread#start()`                                | `RUNNABLE` | -               | -          | -          | -               | -            |
| 进入被 `synchronized` 保护的代码时未获取到锁    | -          | `BLOCKED`       | -          | -          | -               | -            |
| 进入被 `synchronized` 保护的代码时获取到了锁    | -          | -               | `RUNNABLE` | -          | -               | -            |
| `Object#wait()`                                 | -          | `WAITING`       | -          | -          | -               | -            |
| `Thread#join()`                                 | -          | `WAITING`       | -          | -          | -               | -            |
| `LockSupport#park()`                            | -          | `WAITING`       | -          | -          | -               | -            |
| `Thread#sleep(millis)`                          | -          | `TIMED_WAITING` | -          | -          | -               | -            |
| `Object#wait(timeout)`                          | -          | `TIMED_WAITING` | -          | -          | -               | -            |
| `Thread#join(timeout)`                          | -          | `TIMED_WAITING` | -          | -          | -               | -            |
| `LockSupport#parkNanos(nanos)`                  | -          | `TIMED_WAITING` | -          | -          | -               | -            |
| `LockSupport#parkUntil(deadline)`               | -          | `TIMED_WAITING` | -          | -          | -               | -            |
| 获取到 `synchronized` 锁                        | -          | -               | `RUNNABLE` | -          | -               | -            |
| `Object#notify()`                               | -          | -               | -          | `BLOCKED`  | `BLOCKED`       | -            |
| `Object#notifyAll()`                            | -          | -               | -          | `BLOCKED`  | `BLOCKED`       | -            |
| 超时的时间到                                    | -          | -               | -          | -          | `RUNNABLE`      | -            |
| `Thread#join()` 的线程执行结束或被中断          | -          | -               | -          | `RUNNABLE` | `RUNNABLE`      | -            |
| `LockSupport#unpark()`                          | -          | -               | -          | `RUNNABLE` | `RUNNABLE`      | -            |
| `Thread#run()` 方法执行完毕正常退出             | -          | `TERMINATED`    | -          | -          | -               | -            |
| `Thread#run()` 方法中出现未捕获的异常终止了线程 | -          | `TERMINATED`    | -          | -          | -               | -            |

![ThreadLifeCycle](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/juc/thread-lifecycle.png)

接下来我们来看每种状态的定义和相应的状态流转。

# `NEW` 新创建

`NEW` 表示线程刚被创建但还未启动运行的状态：当我们用 `new` 关键字创建了一个线程 `Thread` 对象时，如果还没有开始执行 `Thread#run()` 方法，该线程的状态就是 `NEW`。一旦执行了 `Thread#run()` 方法，该线程的状态就会变为 `RUNNABLE`。

# `RUNNABLE` 可运行

`Java` 中的线程 `RUNNABLE` 状态对应着操作系统线程状态中的 `Running` 和 `Ready`。换句话说，处于 `Java` 中 `RUNNABLE` 状态的线程可能正在执行；也可能没有执行。因为 `CPU` 是流水线作业的，会出现 `Thread#run()` 方法还没有被执行完就发生线程上下文切换的情况，执行该线程的 `CPU` 去执行其它任务，导致该线程短暂地不运行，但它的状态依然保持 `RUNNABLE` 不变，因为 `CPU` 随时可能切换回来继续执行任务。

# 阻塞状态

`Java` 中线程的 `BLOCKED` 被阻塞、`WAITING` 等待和 `TIMED_WAITING` 计时等待统称为阻塞状态。

## `BLOCKED` 被阻塞

从 `RUNNABLE` 状态流转到 `BLOCKED` 被阻塞状态只有一种情况，就是进入被 `synchronized` 保护的临界区代码时没有抢到监视器 `monitor` 锁，无论是进入被 `synchronized` 保护的代码块还是方法，只要没有获取到锁就进入 `BLOCKED` 被阻塞状态。一旦处于 `BLOCKED` 状态的线程争抢到 `monitor` 锁就会回到 `RUNNABLE` 状态。

## `WAITING` 等待

线程进入 `WAITING` 状态有以下三种情况：

- 调用了不带 `timeout` 参数的 `Object#wait()` 方法。
- 调用了不带 `timeout` 参数的 `Thread#join()` 方法。
- 调用了 `LockSupport#park()` 方法。

`Object#wait()` 方法能直接让线程进入 `WAITING` 状态；`Thread#join()` 方法的内部是调用 `Object#wait()` 方法，也会进入 `WAITING` 状态。而 `LockSupport#park()` 方法是 `Lock` 锁 `API` 的基石，很多 `Java` 内置锁在加锁失败时会调用 `LockSupport#park()` 进入 `WAITING` 状态，例如 `ReentrantLock` 等。

处于 `WAITING` 状态的线程不会主动恢复到 `RUNNABLE` 状态。除非发生以下三种情况：

- 调用 `LockSupport#unpark()` 方法。
- `Thread#join()` 的线程执行结束或被中断。
- 其它线程调用 `Object#notify()` 或 `Object#notifyAll()` 方法，先转化为 `BLOCKED` 状态，再间接恢复至 `RUNNABLE` 状态。

其它线程调用 `Object#notify()` 或 `Object#notifyAll()` 方法时会持有 `monitor` 锁，处于 `WAITING` 状态的线程刚被唤醒时拿不到锁，会先进入 `BLOCKED` 状态，直到调用 `notify()/notifyAll()` 方法的线程执行完毕并释放 `monitor` 锁，才轮到它去争抢锁，如果能抢到锁，就会恢复至 `RUNNABLE` 状态。

## `TIMED_WAITING` 计时等待

线程进入 `TIMED_WAITING` 状态有以下四种情况：

- 调用了带 `millis` 参数的 `Thread#sleep(millis)` 方法。
- 调用了带 `timeout` 参数的 `Object#wait(timeout)` 方法。
- 调用了带 `millis` 参数的 `Thread#join(timeout)` 方法。
- 调用了带 `nanos` 参数的 `LockSupport#parkNanos(nanos)` 方法和带 `deadline` 参数的 `LockSupport#parkUntil(deadline)` 方法。

`TIMED_WAITING` 状态和 `WAITING` 状态的区别仅在于是否有超时时间，`TIMED_WAITING` 状态在等待超时后会由系统自动唤醒。

## `TERMINATED` 被终止

线程进入 `TERMINATED` 状态有以下两种情况：

- `run` 方法执行完毕，线程正常退出终止。
- 出现运行时异常，终止了 `run` 方法，线程异常退出终止。

# 总结

本文介绍了线程的六种状态：`NEW`、`RUNNABLE`、`BLOCKED`、`WAITING`、`TIMED_WAITING` 和 `TERMINATED`。状态之间的转化严格按照状态机进行扭转。
