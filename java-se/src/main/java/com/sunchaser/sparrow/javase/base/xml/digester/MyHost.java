package com.sunchaser.sparrow.javase.base.xml.digester;

import lombok.Data;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/23
 */
@Data
public class MyHost {
    private String name;
    private String appBase;
    private String unpackWARs;
    private String autoDeploy;
}
