package com.sunchaser.sparrow.javase.base.xml.digester;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
public class MyConnector {
    private Integer port;
    private String protocol;
    private Integer connectionTimeout;
    private Integer redirectPort;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getRedirectPort() {
        return redirectPort;
    }

    public void setRedirectPort(Integer redirectPort) {
        this.redirectPort = redirectPort;
    }

    @Override
    public String toString() {
        return "MyConnector{" +
                "port=" + port +
                ", protocol='" + protocol + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", redirectPort=" + redirectPort +
                '}';
    }
}
