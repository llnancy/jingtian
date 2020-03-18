package com.sunchaser.dubbo.provider.facade.impl;

import com.sunchaser.dubbo.provider.facade.TestFacade;
import org.springframework.stereotype.Service;

/**
 * @author sunchaser
 * @date 2020/1/29
 * @description
 * @since 1.0
 */
@Service(value = "testFacade")
public class TestFacadeImpl implements TestFacade {
    @Override
    public void test() {
        System.out.println(123);
    }
}
