package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import org.xml.sax.Attributes;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public class ObjectCreateProcessor extends AbstractBaseProcessor {
    private final String className;

    public ObjectCreateProcessor(String className) {
        this.className = className;
    }

    @Override
    public void processStart(String qName, Attributes attributes) throws Exception {
        Object instance = this.getMyDigester().getClass().getClassLoader().loadClass(className).newInstance();
        getMyDigester().push(instance);
        LOGGER.info("ObjectCreateProcessor#processStart stack push:{}", instance);
    }

    @Override
    public void processEnd(String qName) throws Exception {
        Object pop = getMyDigester().pop();
        LOGGER.info("ObjectCreateProcessor#processEnd stack pop:{}", pop);
    }
}
