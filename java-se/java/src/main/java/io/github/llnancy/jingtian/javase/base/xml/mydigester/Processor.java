package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import org.xml.sax.Attributes;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public interface Processor {
    default void processStart(String qName, Attributes attributes) throws Exception {};
    default void processEnd(String qName) throws Exception {};
    void setMyDigester(MyDigester myDigester);
}
