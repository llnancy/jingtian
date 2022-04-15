package com.sunchaser.sparrow.javase.base.xml.mydigester;

import org.apache.commons.beanutils.MethodUtils;
import org.xml.sax.Attributes;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public class InvokeMethodProcessor extends AbstractBaseProcessor {
    private final String methodName;
    private final String paramType;

    public InvokeMethodProcessor(String methodName, String paramType) {
        this.methodName = methodName;
        this.paramType = paramType;
    }

    @Override
    public void processStart(String qName, Attributes attributes) throws Exception {
        Class<?>[] paramTypes = new Class<?>[1];
        paramTypes[0] = getMyDigester().getClass().getClassLoader().loadClass(paramType);
        Object arg = getMyDigester().peek(0);
        Object obj = getMyDigester().peek(1);
        LOGGER.info("InvokeMethodProcessor#processStart invoke obj:{} method:{} with args:{}", obj, methodName, arg);
        MethodUtils.invokeMethod(obj, methodName, new Object[]{ arg }, paramTypes);
    }
}
