package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/19
 */
@Data
public class Foo {
    private List<Bar> barList = new ArrayList<>();

    public void addBar(Bar bar) {
        barList.add(bar);
    }
}
