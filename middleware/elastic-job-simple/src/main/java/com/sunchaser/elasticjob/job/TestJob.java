package com.sunchaser.elasticjob.job;

import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.simple.SimpleJob;

import java.util.Date;

/**
 * @author sunchaser
 * @date 2020/1/2
 * @description
 * @since 1.0
 */
public class TestJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(new Date() + "  1111");
    }
}
