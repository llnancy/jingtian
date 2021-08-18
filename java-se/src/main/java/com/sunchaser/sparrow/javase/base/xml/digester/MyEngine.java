package com.sunchaser.sparrow.javase.base.xml.digester;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
public class MyEngine {
    private String name;
    private String defaultHost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultHost() {
        return defaultHost;
    }

    public void setDefaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
    }

    @Override
    public String toString() {
        return "MyEngine{" +
                "name='" + name + '\'' +
                ", defaultHost='" + defaultHost + '\'' +
                '}';
    }
}
