package io.github.llnancy.jingtian.javaee.log.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * log4j2
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/11
 */
public class Log4j2Log {

    private static final Logger LOGGER = LogManager.getLogger(Log4j2Log.class);

    public static void main(String[] args) {
        LOGGER.fatal("fatal");
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
