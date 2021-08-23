package com.sunchaser.sparrow.javase.base.xml.mydigester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public abstract class AbstractBaseProcessor implements Processor {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseProcessor.class);
    private MyDigester myDigester;

    public MyDigester getMyDigester() {
        return myDigester;
    }

    @Override
    public void setMyDigester(MyDigester myDigester) {
        this.myDigester = myDigester;
    }
}
