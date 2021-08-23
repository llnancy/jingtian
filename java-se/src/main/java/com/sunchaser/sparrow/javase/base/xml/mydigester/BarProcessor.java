package com.sunchaser.sparrow.javase.base.xml.mydigester;

import org.xml.sax.Attributes;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public class BarProcessor extends AbstractBaseProcessor {
    @Override
    public void processStart(String qName, Attributes attributes) {
        Bar bar = new Bar();
        for (int i = 0; i < attributes.getLength(); i++) {
            if ("name".equals(attributes.getQName(i))) {
                bar.setName(attributes.getValue(i));
            }
        }
        Foo foo = getMyDigester().get(0);
        foo.addBar(bar);
        getMyDigester().push(bar);
        LOGGER.info("BarProcessor#processStart stack push bar:{}", bar);
    }

    @Override
    public void processEnd(String qName) {
        Bar pop = getMyDigester().pop();
        LOGGER.info("BarProcessor#processEnd endElement, stack pop Bar:{}", pop);
    }
}
