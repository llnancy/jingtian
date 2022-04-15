package com.sunchaser.sparrow.javase.shell;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sunchaser.sparrow.javase.shell.ShellCommandExecutor.Status.SUCCESS;
import static com.sunchaser.sparrow.javase.shell.ShellCommandExecutor.Status.WARN;

/**
 * 对Java执行shell命令的封装
 *
 * @see "org.apache.zookeeper.Shell"
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/21
 */
@Slf4j
public class ShellCommandExecutor {

    private final Command command;

    private final Result result;

    public Result getResult() {
        return result;
    }

    /**
     * default timeout interval. timeunit is millis
     */
    private static final Long DEFAULT_TIMEOUT_INTERVAL = 0L;

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
        command.setCommand(commandStrings);
        if (Objects.nonNull(dir)) {
            command.setDir(dir);
        }
        if (Objects.nonNull(env)) {
            command.setEnvironment(env);
        }
        if (Objects.nonNull(timeOutInterval)) {
            command.setTimeOutInterval(timeOutInterval);
        } else {
            command.setTimeOutInterval(DEFAULT_TIMEOUT_INTERVAL);
        }
        result = new Result();
        result.setCommand(commandStrings);
    }

    public void execute() {
        this.run();
    }

    protected void run() {
        Worker worker = null;
        Process process = null;
        try {
            ProcessBuilder builder = prepareProcessBuilder();
            process = builder.start();
            worker = new Worker(process, result);
            worker.start();
            Long timeOutInterval = command.getTimeOutInterval();
            worker.join(timeOutInterval);
            if (Objects.isNull(result.exitCode)) {
                throw new TimeoutException("CommandExecutor执行命令" + command + "超时, timeOutInterval=" + timeOutInterval);
            }
        } catch (InterruptedException e) {
            log.error("ShellCommandExecutor#Worker#join interrupted exception.", e);
            worker.interrupt();
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("ShellCommandExecutor#run error");
            throw new ShellCommandExecutorException(e);
        } finally {
            if (Objects.nonNull(process)) {
                process.destroy();
            }
        }
    }

    private ProcessBuilder prepareProcessBuilder() {
        ProcessBuilder builder = new ProcessBuilder(command.getCommand());

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
            return execute(command, dir, 0L);
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
            return execute(commands, dir, 0L);
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
            ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor(commands, dir, env, timeout);
            shellCommandExecutor.execute();
            return shellCommandExecutor.getResult();
        }

        public static File getSafeFile(String navigatePath) {
            return (navigatePath == null || navigatePath.length() == 0) ? null : new File(navigatePath);
        }
    }

    /**
     * 任务执行者
     */
    private static class Worker extends Thread {

        /**
         * Process对象，用来获取标准输入流
         */
        private final Process process;

        /**
         * 执行结果
         */
        private final Result result;

        /**
         * 收集缓冲区字符的线程池
         */
        private final ExecutorService executorService;

        /* constructors begin */
        public Worker(Process process, Result result) {
            this.process = process;
            this.result = result;
            this.executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
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
            });
        }

        public Worker(Process process, Result result, ExecutorService executorService) {
            this.process = process;
            this.result = result;
            this.executorService = executorService;
        }
        /* constructors end */

        /**
         * worker start invoke
         */
        @Override
        public void run() {
            try {
                InputStream inputStream = process.getInputStream();
                InputStream errorStream = process.getErrorStream();
                executorService.execute(new Task(inputStream, result.execInfoBuilder));
                executorService.execute(new Task(errorStream, result.execErrorBuilder));
                int exitCode = process.waitFor();
                result.exitCode = exitCode;
                if (exitCode == 0) {
                    if (log.isDebugEnabled()) {
                        log.debug("ShellCommandExecutor执行shell命令的子线程正常执行完成结束. exitCode == 0");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("ShellCommandExecutor执行shell命令的子线程执行失败异常结束. exitCode == {}", exitCode);
                    }
                }
            } catch (InterruptedException e) {
                log.error("ShellCommandExecutor.Worker#run interrupted exception", e);
                throw new ShellCommandExecutorException(e);
            }
        }
    }

    /**
     * 任务：收集缓冲区中的字符内容
     */
    private static class Task implements Runnable {

        /**
         * 标准输入流
         */
        private final InputStream is;

        /**
         * 从流中读取的字符
         */
        private final StringBuilder output;

        /**
         * 行分隔符
         */
        private static final String LINE_SEPARATOR = "line.separator";

        /**
         * Constructor
         * @param is InputStream
         * @param output StringBuilder
         */
        public Task(InputStream is, StringBuilder output) {
            this.is = is;
            this.output = output;
        }

        /**
         * the task
         */
        @Override
        public void run() {
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    output.append(line);
                    output.append(System.getProperty(LINE_SEPARATOR));
                }
            } catch (IOException e) {
                log.error("[ShellCommandExecutor.Task] Error reading the error stream", e);
                throw new ShellCommandExecutorException(e);
            }
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
            log.info("command: {}", Arrays.toString(this.getCommand()));
            if (this.execInfoBuilder.length() != 0) {
                log.info("info: {}", this.getExecInfoBuilder().toString());
            }
            if (this.execErrorBuilder.length() != 0) {
                log.info("error: {}", this.getExecErrorBuilder().toString());
            }
        }
    }

    /**
     * 执行状态
     */
    public enum Status {
        SUCCESS, WARN,
        ;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public static class ShellCommandExecutorException extends RuntimeException {

        private static final long serialVersionUID = 2888537369191849470L;

        public ShellCommandExecutorException(Throwable cause) {
            super(cause);
        }
    }
}
