package io.github.llnancy.jingtian.javaee.log.jul;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * java.util.Logging
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/10
 */
public class JulLog {

    private static final Logger LOGGER = Logger.getLogger(JulLog.class.getName());

    public static void main(String[] args) throws IOException {
        // 基本使用
        basicUse();

        // 日志级别。默认日志输出级别为 info。
        logLevel(LOGGER);

        // 自定义配置日志级别
        customLogLevel();

        // Logger 对象父子关系
        loggerExtend();

        // 使用自定义配置文件
        useLoggingProperties();
    }

    private static void basicUse() {
        LOGGER.info("This is a info log.");
        LOGGER.log(Level.INFO, "This is a simple log.");
        LOGGER.log(Level.INFO, "This is a log with one parameter. {0}", "param1");
        LOGGER.log(Level.INFO, "This is a log with multiple parameters. {0}, {1}", new Object[]{"param1", "param2"});
    }

    private static void logLevel(Logger logger) {
        logger.severe("severe level.");
        logger.warning("warning level.");
        logger.info("info level.");
        logger.config("config level.");
        logger.fine("fine level.");
        logger.finer("finer level.");
        logger.finest("finest level.");
    }

    private static void customLogLevel() throws IOException {
        // 关闭系统默认配置
        LOGGER.setUseParentHandlers(false);
        // 简单日志输出格式转化器
        SimpleFormatter formatter = new SimpleFormatter();
        // 控制台输出处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        LOGGER.addHandler(handler);
        // 文件输出处理器（绝对路径或相对于项目根目录的路径）
        FileHandler fileHandler = new FileHandler("logs/jul.log");
        fileHandler.setFormatter(formatter);
        LOGGER.addHandler(fileHandler);
        // 配置级别
        LOGGER.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        // 输出
        logLevel(LOGGER);
    }

    private static void loggerExtend() {
        // logger1 继承 logger2
        Logger logger1 = Logger.getLogger("com.sunchaser");
        Logger logger2 = Logger.getLogger("com");

        // logger1Parent == logger2
        // 包名存在包含关系，logger2 是 logger1 的 parent（继承）
        Logger logger1Parent = logger1.getParent();
        System.out.println(logger1Parent);
        System.out.println(logger2);

        // 所有日志记录器的顶级父元素 LogManager$RootLogger，其 name 为空字符串
        // logger2 的父元素是 LogManager$RootLogger
        Logger logger2Parent = logger2.getParent();
        String logger2ParentName = logger2.getParent().getName();
        System.out.println("logger2 parent: " + logger2Parent);
        System.out.println("logger2 parent name: " + logger2ParentName);

        // 设置 logger2 的日志级别
        customLogLevel(logger2);

        // logger1 的日志级别继承了 logger2
        logLevel(logger1);
    }

    private static void customLogLevel(Logger logger) {
        // 关闭系统默认配置（断绝父子关系）
        logger.setUseParentHandlers(false);
        // 简单日志输出格式转化器
        SimpleFormatter formatter = new SimpleFormatter();
        // 控制台输出处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        logger.addHandler(handler);
        // 配置级别
        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
    }

    private static void useLoggingProperties() throws IOException {
        // LogManager.getLogManager(); 方法中会加载配置文件
        // 关键方法如下：
        // java.util.logging.LogManager.ensureLogManagerInitialized
        // owner.readPrimordialConfiguration();
        // readConfiguration()
        // 默认配置文件路径为：/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/logging.properties
        // 即：$JAVA_HOME/jre/lib/logging.properties

        // 使用自定义配置文件
        // 通过类加载器读取配置文件
        InputStream ins = JulLog.class.getClassLoader().getResourceAsStream("logging.properties");
        // 创建 LogManager 对象
        LogManager logManager = LogManager.getLogManager();
        // 通过 LogManager 对象加载配置文件
        logManager.readConfiguration(ins);
        Logger logger = Logger.getLogger(JulLog.class.getName());
        logLevel(logger);
    }
}
