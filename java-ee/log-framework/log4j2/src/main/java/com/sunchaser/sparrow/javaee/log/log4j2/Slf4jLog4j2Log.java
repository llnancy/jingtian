package com.sunchaser.sparrow.javaee.log.log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 slf4j 作为日志门面，使用 log4j2 作为日志实现
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/11
 */
public class Slf4jLog4j2Log {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jLog4j2Log.class);

    public static void main(String[] args) {
        // 此处 LOGGER 使用 log4j2 进行打印
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
