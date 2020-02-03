package com.sunchaser.xxljob.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * @author sunchaser
 * @date 2020/1/19
 * @description
 * @since 1.0
 */
@JobHandler(value = "demoJobHandler")
@Component
public class DemoJob extends IJobHandler {
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        try {
            System.out.println(111111111);
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }
}
