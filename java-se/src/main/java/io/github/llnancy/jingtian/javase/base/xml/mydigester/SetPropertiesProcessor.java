package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.Attributes;

import java.util.Map;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public class SetPropertiesProcessor extends AbstractBaseProcessor {
    @Override
    public void processStart(String qName, Attributes attributes) throws Exception {
        Map<String, String> properties = Maps.newHashMap();
        for (int i = 0; i < attributes.getLength(); i++) {
            properties.put(attributes.getQName(i), attributes.getValue(i));
        }
        Object top = getMyDigester().peek();
        LOGGER.info("SetPropertiesProcessor#processStart stack peek:{}", top);
        BeanUtils.populate(top, properties);
    }
}
