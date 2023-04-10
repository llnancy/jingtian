package io.github.llnancy.jingtian.javase.base.xml.digester;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
@Data
public class MyService {
    private String name;

    private List<MyConnector> myConnectorList = new ArrayList<>();

    private MyEngine myEngine;

    public void addMyConnector(MyConnector myConnector) {
        myConnectorList.add(myConnector);
    }
}
