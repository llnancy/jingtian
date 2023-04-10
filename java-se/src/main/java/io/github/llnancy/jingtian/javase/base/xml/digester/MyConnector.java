package io.github.llnancy.jingtian.javase.base.xml.digester;

import lombok.Data;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
@Data
public class MyConnector {
    private Integer port;
    private String protocol;
    private Integer connectionTimeout;
    private Integer redirectPort;
}
