package com.sunchaser.sparrow.springboot.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Client {
    @Autowired
    private IdentityDefinitionFactory identityDefinitionFactory;
    @Test
    public void testBean() {
        System.out.println(identityDefinitionFactory.getIdentity("ass"));
    }
}
