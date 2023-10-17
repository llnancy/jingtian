package io.github.llnancy.jingtian.javaee.log.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * org.apache.commons.logging.Log
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/10
 */
public class JclLog {

    public static void main(String[] args) {
        // 获取日志器对象
        Log log = LogFactory.getLog(JclLog.class);
        // 默认使用 java.util.logging 实现，当引入 log4j 依赖后使用 log4j 实现。
        log.info("This is a jcl log.");

        /*
        原理：
        1. 通过 LogFactory 动态加载 Log 实现类。
        2. 日志门面支持的日志实现类数组
        private static final String[] classesToDiscover = {
                "org.apache.commons.logging.impl.Log4JLogger",
                "org.apache.commons.logging.impl.Jdk14Logger",
                "org.apache.commons.logging.impl.Jdk13LumberjackLogger",
                "org.apache.commons.logging.impl.SimpleLog"
        };
        3. 获取具体的日志实现类
        for(int i=0; i<classesToDiscover.length && result == null; ++i) {
            result = createLogFromClass(classesToDiscover[i], logCategory, true);
        }
        通过 for 循环按照 classesToDiscover 数组的顺序反射加载实现类，数组写死在代码中，无法进行动态扩展。
         */
    }
}
