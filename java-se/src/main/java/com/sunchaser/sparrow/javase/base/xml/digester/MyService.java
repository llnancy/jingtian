package com.sunchaser.sparrow.javase.base.xml.digester;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
public class MyService {
    private String name;

    private List<MyConnector> myConnectorList = new ArrayList<>();

    private MyEngine myEngine;

    public void addMyConnector(MyConnector myConnector) {
        myConnectorList.add(myConnector);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MyConnector> getMyConnectorList() {
        return myConnectorList;
    }

    public void setMyConnectorList(List<MyConnector> myConnectorList) {
        this.myConnectorList = myConnectorList;
    }

    public MyEngine getMyEngine() {
        return myEngine;
    }

    public void setMyEngine(MyEngine myEngine) {
        this.myEngine = myEngine;
    }

    @Override
    public String toString() {
        return "MyService{" +
                "name='" + name + '\'' +
                ", myConnectorList=" + myConnectorList +
                ", myEngine=" + myEngine +
                '}';
    }
}
