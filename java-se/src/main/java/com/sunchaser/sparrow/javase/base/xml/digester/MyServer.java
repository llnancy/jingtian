package com.sunchaser.sparrow.javase.base.xml.digester;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
@Data
public class MyServer {
    private Integer port;
    private String shutdown;

    private List<MyService> myServiceList = new ArrayList<>();

    public void addMyService(MyService myService) {
        myServiceList.add(myService);
    }
}
