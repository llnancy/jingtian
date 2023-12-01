package io.github.llnancy.jingtian.javase.base.xml.digester;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
@Data
public class MyEngine {
    private String name;
    private String defaultHost;

    private List<MyHost> myHostList = new ArrayList<>();

    public void addMyHost(MyHost myHost) {
        myHostList.add(myHost);
    }
}
