package io.github.llnancy.jingtian.javaee.log.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * org.slf4j.Logger
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/10
 */
public class Slf4jLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jLog.class);

    public static void main(String[] args) {
        // 日志级别
        LOGGER.error("error");
        LOGGER.warn("warn");
        // 默认级别是 info
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");

        // 占位符输出
        LOGGER.info("This is a log with multiple parameters. {}, {}.", "param1", "param2");

        // 输出异常信息
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            LOGGER.error("出现异常", e);
        }
    }
}
