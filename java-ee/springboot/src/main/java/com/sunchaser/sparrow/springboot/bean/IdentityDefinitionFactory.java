package com.sunchaser.sparrow.springboot.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@Component
public class IdentityDefinitionFactory {
    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String,Class<? extends IdentityDefinition>> importHfMap = new HashMap<>();

    {
        importHfMap.put("sunchaser", BossIdentityDefinition.class);
    }

    public IdentityDefinition getIdentity(String identity) {
        Class<? extends IdentityDefinition> srcClazz = importHfMap.get(identity);
        if (srcClazz == null) return applicationContext.getBean(AbstractIdentityDefinition.class);
        return applicationContext.getBean(srcClazz);
    }
}
