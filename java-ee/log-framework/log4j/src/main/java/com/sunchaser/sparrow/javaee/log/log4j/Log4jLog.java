package com.sunchaser.sparrow.javaee.log.log4j;

import org.apache.log4j.Logger;

/**
 * org.apache.log4j
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/10
 */
public class Log4jLog {

    public static void main(String[] args) {
        // 初始化配置信息（不使用配置文件）
        // BasicConfigurator.configure();

        // 获取日志器
        Logger logger = Logger.getLogger(Log4jLog.class);
        // 打印日志
        logger.info("This is a log4j log info.");

        // 日志级别
        logLevel(logger);

        // for (int i = 0; i < 10000; i++) {
        //     logLevel(logger);
        // }

        /*
        Log4j 包含以下三大组件：
        1. Loggers：日志记录器。控制日志输出级别与日志是否输出。
        2. Appenders：输出端。指定日志的输出方式，例如输出到控制台或文件等。
        3. Layouts：日志格式化器。控制日志信息的输出格式。

        常用 Appenders：
        1. ConsoleAppender：将日志输出到控制台。
        2. FileAppender：将日志输出到文件。
        3. DailyRollingFileAppender：将日志输出到一个文件，并且每天输出到一个新的文件。
        4. RollingFileAppender：将日志输出到一个文件，并且指定文件的尺寸，当文件大小达到指定尺寸时，自动把文件改名，同时生成一个新的文件。
        5. JDBCAppender：将日志输出到数据库。

        常用 Layout：
        1. HTMLLayout：HTML表格形式。
        2. SimpleLayout：简单格式。[info-message]
        3. PatternLayout：自定义格式。
         */

    }

    private static void logLevel(Logger logger) {
        // 严重错误
        logger.fatal("fatal level.");
        // 错误
        logger.error("error level.");
        // 警告
        logger.warn("warn level.");
        // 正常
        logger.info("info level.");
        // 调试
        logger.debug("debug level.");
        // 追踪
        logger.trace("trace level.");
    }
}
