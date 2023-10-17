package io.github.llnancy.jingtian.javaee.log.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logback
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/11
 */
public class LogbackLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackLog.class);

    public static void main(String[] args) {
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info");
        // 默认是 debug 级别
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
