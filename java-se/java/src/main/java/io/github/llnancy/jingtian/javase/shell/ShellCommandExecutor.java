package io.github.llnancy.jingtian.javase.shell;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.llnancy.jingtian.javase.shell.ShellCommandExecutor.Status.SUCCESS;
import static io.github.llnancy.jingtian.javase.shell.ShellCommandExecutor.Status.WARN;

/**
 * 对 Java 执行 shell 命令的封装
 *
 * @author sunchaser admin@lilu.org.cn
 * @see "org.apache.zookeeper.Shell"
 * @since JDK8 2022/2/21
 */
@Slf4j
public class ShellCommandExecutor {

    /**
     * 线程池
     */
    private static final ExecutorService SHELL_COMMAND_EXECUTOR;

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR;

    /**
     * default timeout interval, 60s. timeunit is millis
     */
    private static final Long DEFAULT_TIMEOUT_INTERVAL;

    static {
        int cpu = Runtime.getRuntime().availableProcessors();
        LOGGER.info("Runtime.getRuntime().availableProcessors() = {}", cpu);
        SHELL_COMMAND_EXECUTOR = Executors.newFixedThreadPool(
                cpu - 1,
                new ThreadFactory() {
                    private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(@Nonnull Runnable r) {
                        Thread thread = this.defaultFactory.newThread(r);
                        if (!thread.isDaemon()) {
                            thread.setDaemon(true);
                        }
                        thread.setName("shell-command-" + this.threadNumber.getAndIncrement());
                        return thread;
                    }
                }
        );
        LINE_SEPARATOR = "line.separator";
        DEFAULT_TIMEOUT_INTERVAL = 60000L;
    }

    /**
     * command
     */
    private final Command command;

    /**
     * result
     */
    private final Result result;

    /* getter */
    public Result getResult() {
        return result;
    }

    /* constructors begin */
    public ShellCommandExecutor(String[] commandStrings) {
        this(commandStrings, (File) null);
    }

    public ShellCommandExecutor(String[] commandStrings, Long timeOutInterval) {
        this(commandStrings, null, timeOutInterval);
    }

    public ShellCommandExecutor(String[] commandStrings, File dir) {
        this(commandStrings, dir, null, DEFAULT_TIMEOUT_INTERVAL);
    }

    public ShellCommandExecutor(String[] commandStrings, File dir, Long timeOutInterval) {
        this(commandStrings, dir, null, timeOutInterval);
    }

    public ShellCommandExecutor(String[] commandStrings, File dir, Map<String, String> env) {
        this(commandStrings, dir, env, DEFAULT_TIMEOUT_INTERVAL);
    }

    public ShellCommandExecutor(String[] commandStrings, File dir, Map<String, String> env, Long timeOutInterval) {
        command = new Command();
        result = new Result();
        command.setCommand(commandStrings);
        result.setCommand(commandStrings);
        if (Objects.nonNull(dir)) {
            command.setDir(dir);
            result.setDir(dir);
        }
        if (Objects.nonNull(env)) {
            command.setEnvironment(env);
            result.setEnvironment(env);
        }
        if (Objects.nonNull(timeOutInterval)) {
            command.setTimeOutInterval(timeOutInterval);
            result.setTimeOutInterval(timeOutInterval);
        } else {
            command.setTimeOutInterval(0L);
            result.setTimeOutInterval(0L);
        }
    }
    /* constructors end */

    public Result execute() {
        Future<Result> future = SHELL_COMMAND_EXECUTOR.submit(() -> {
            InputStream is = null;
            Process process = null;
            try {
                process = prepareProcessBuilder().start();
                is = process.getInputStream();
                StringBuilder execInfoBuilder = result.getExecInfoBuilder();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    execInfoBuilder.append(line);
                    execInfoBuilder.append(System.getProperty(LINE_SEPARATOR));
                }
                int exitCode = process.waitFor();
                result.exitCode = exitCode;
                if (exitCode == 0) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("ShellCommandExecutor执行shell命令的子线程正常执行完成结束. exitCode == 0");
                    }
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("ShellCommandExecutor执行shell命令的子线程执行失败异常结束. exitCode == {}", exitCode);
                    }
                }
                result.print();
            } catch (Exception e) {
                LOGGER.error("[ShellCommandExecutor] execute error", e);
                throw new ShellCommandExecutorException(e);
            } finally {
                if (Objects.nonNull(is)) {
                    try {
                        is.close();
                    } catch (Exception e) {
                        // 静默关闭
                    }
                }
                if (Objects.nonNull(process)) {
                    process.destroy();
                }
            }
            return result;
        });
        Long timeOutInterval = command.getTimeOutInterval();
        if (timeOutInterval > 0) {
            try {
                future.get(timeOutInterval, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                LOGGER.error("[ShellCommandExecutor] future get timeout", e);
                future.cancel(true);
            } catch (Exception e) {
                LOGGER.error("[ShellCommandExecutor] future get error", e);
            }
        }
        return result;
    }

    private ProcessBuilder prepareProcessBuilder() {
        ProcessBuilder builder = new ProcessBuilder(command.getCommand());
        builder.redirectErrorStream(true);

        Map<String, String> environment = command.getEnvironment();
        File dir = command.getDir();
        if (Objects.nonNull(environment)) {
            builder.environment().putAll(environment);
        }

        if (Objects.nonNull(dir)) {
            builder.directory(dir);
        }
        return builder;
    }

    /**
     * 执行器工具类，提供静态方法对外使用
     */
    public static class ShellCommandExecutors {
        private ShellCommandExecutors() {
        }

        public static Result execute(String command) {
            return execute(command, (String) null);
        }

        public static Result execute(String command, Long timeout) {
            return execute(command, (String) null, timeout);
        }

        public static Result execute(String command, String navigatePath) {
            return execute(command, getSafeFile(navigatePath));
        }

        public static Result execute(String command, File dir) {
            return execute(command, dir, DEFAULT_TIMEOUT_INTERVAL);
        }

        public static Result execute(String command, String navigatePath, Long timeout) {
            return execute(command, getSafeFile(navigatePath), timeout);
        }

        public static Result execute(String command, File dir, Long timeout) {
            return execute(command, dir, null, timeout);
        }

        public static Result execute(String command, String navigatePath, Map<String, String> env, Long timeout) {
            return execute(command, getSafeFile(navigatePath), env, timeout);
        }

        public static Result execute(String command, File dir, Map<String, String> env, Long timeout) {
            return execute(new String[]{"/bin/sh", "-c", command}, dir, env, timeout);
        }

        public static Result execute(String... command) {
            return execute(command, (String) null);
        }

        public static Result execute(String[] commands, String navigatePath) {
            return execute(commands, getSafeFile(navigatePath));
        }

        public static Result execute(String[] commands, File dir) {
            return execute(commands, dir, DEFAULT_TIMEOUT_INTERVAL);
        }

        public static Result execute(String[] commands, String navigatePath, Long timeout) {
            return execute(commands, getSafeFile(navigatePath), timeout);
        }

        public static Result execute(String[] commands, File dir, Long timeout) {
            return execute(commands, dir, null, timeout);
        }

        public static Result execute(String[] commands, String navigatePath, Map<String, String> env, Long timeout) {
            return execute(commands, getSafeFile(navigatePath), env, timeout);
        }

        public static Result execute(String[] commands, File dir, Map<String, String> env, Long timeout) {
            return new ShellCommandExecutor(commands, dir, env, timeout).execute();
        }

        public static File getSafeFile(String navigatePath) {
            return (navigatePath == null || navigatePath.length() == 0) ? null : new File(navigatePath);
        }
    }

    /**
     * Command
     */
    @Data
    public static class Command {
        /**
         * 待执行的shell命令
         */
        private String[] command;

        /**
         * 环境变量
         * env for the command execution
         */
        private Map<String, String> environment;

        /**
         * shell执行目录
         */
        private File dir;

        /**
         * 执行超时时间
         */
        private Long timeOutInterval;
    }

    /**
     * 执行的结果
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Result extends Command {

        /**
         * 执行成功输出的正常信息
         */
        private final StringBuilder execInfoBuilder = new StringBuilder();

        /**
         * 执行失败输出的错误信息
         */
        private final StringBuilder execErrorBuilder = new StringBuilder();

        /**
         * 状态码
         */
        private Integer exitCode;

        /**
         * 状态码=0表示执行成功
         *
         * @return CommandExecutorStatus.name();
         */
        public String status() {
            return exitCode == 0 ? SUCCESS.toString() : WARN.toString();
        }

        /**
         * 打印输出执行的shell命令和缓冲区内容
         */
        public void print() {
            LOGGER.info("command: {}, exitCode: {}, timeout={}ms, execute result:\n{}", Arrays.toString(this.getCommand()), exitCode, this.getTimeOutInterval(), this.getExecInfoBuilder().toString());
        }
    }

    /**
     * 执行状态
     */
    public enum Status {
        SUCCESS, WARN,
        ;
    }

    public static class ShellCommandExecutorException extends RuntimeException {

        private static final long serialVersionUID = 2888537369191849470L;

        public ShellCommandExecutorException(Throwable cause) {
            super(cause);
        }
    }
}
