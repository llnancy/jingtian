package io.github.llnancy.jingtian.javaee.log.slf4j;

import org.apache.log4j.Logger;

/**
 * Upgrade from log4j to logback
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/11
 */
public class Log4jLog {

    private static final Logger LOGGER = Logger.getLogger(Log4jLog.class);

    public static void main(String[] args) {
        LOGGER.info("Upgrade from log4j to logback.");
    }
}
