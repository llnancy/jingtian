package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import org.xml.sax.Attributes;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public class FooProcessor extends AbstractBaseProcessor {
    @Override
    public void processStart(String qName, Attributes attributes) throws Exception {
        Foo foo = new Foo();
        getMyDigester().push(foo);
        LOGGER.info("FooProcessor#processStart stack push foo:{}", foo);
    }

    @Override
    public void processEnd(String qName) throws Exception {
        Foo pop = getMyDigester().pop();
        LOGGER.info("FooProcessor#processEnd endElement, stack pop Foo:{}", pop);
    }
}
