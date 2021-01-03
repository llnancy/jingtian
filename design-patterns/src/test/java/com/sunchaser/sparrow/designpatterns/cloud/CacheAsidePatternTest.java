package com.sunchaser.sparrow.designpatterns.cloud;

import com.google.common.collect.Maps;
import com.sunchaser.sparrow.designpatterns.common.model.request.CacheAsideRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheAsidePatternTest {
    @Autowired
    private CacheAsidePattern cacheAsidePattern;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final Map<String,String> db = Maps.newHashMap();

    @Before
    public void setUp() throws Exception {
        db.put("defaultUser","defaultValue");
        db.put("user1","value1");
        db.put("user2","value2");
        db.put("user3","value3");
        db.put("user4","value4");
    }

    @Test
    public void testSelectCacheOrDb() {
        CacheAsideRequest<String> cacheAsideRequest = new CacheAsideRequest<>();
        cacheAsideRequest.setCacheKey("test-key");
        cacheAsideRequest.setExpiredTime(100);
        cacheAsideRequest.setTimeUnit(TimeUnit.SECONDS);
        cacheAsideRequest.setDbSelector(() -> db.get("user1"));
        cacheAsideRequest.setDefaultValue(db.get("defaultUser"));
        String s = cacheAsidePattern.selectCacheOrDb(cacheAsideRequest);
        System.out.println(s);
    }

    @Test
    public void testUpdate() {
        db.put("user1","updateValue1");
        redisTemplate.delete("test-key");
    }
}