package com.sunchaser.sparrow.javase.base.xml.digester;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
public class MyServer {
    private Integer port;
    private String shutdown;

    private List<MyService> myServiceList = new ArrayList<>();

    public void addMyService(MyService myService) {
        myServiceList.add(myService);
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getShutdown() {
        return shutdown;
    }

    public void setShutdown(String shutdown) {
        this.shutdown = shutdown;
    }

    public List<MyService> getMyServiceList() {
        return myServiceList;
    }

    public void setMyServiceList(List<MyService> myServiceList) {
        this.myServiceList = myServiceList;
    }

    @Override
    public String toString() {
        return "MyServer{" +
                "port=" + port +
                ", shutdown='" + shutdown + '\'' +
                ", myServiceList=" + myServiceList +
                '}';
    }
}
