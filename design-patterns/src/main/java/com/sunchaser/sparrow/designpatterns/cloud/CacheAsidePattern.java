package com.sunchaser.sparrow.designpatterns.cloud;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sunchaser.sparrow.designpatterns.common.enums.CacheStrategyEnum;
import com.sunchaser.sparrow.designpatterns.common.model.request.CacheAsideRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/3
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheAsidePattern {
    private final RedisTemplate<String,String> redisTemplate;

    public <T> T selectCacheOrDb(CacheAsideRequest<T> cacheAsideRequest) {
        try {
            String cacheKey = cacheAsideRequest.getCacheKey();
            // random 防止雪崩
            int expiredTime = cacheAsideRequest.getExpiredTime() + ThreadLocalRandom.current().nextInt(60);
            TimeUnit timeUnit = cacheAsideRequest.getTimeUnit();
            CacheStrategyEnum cacheStrategyEnum = cacheAsideRequest.getCacheStrategyEnum();
            String cacheValueString = redisTemplate.opsForValue().get(cacheKey);
            if (StringUtils.isEmpty(cacheValueString)) {
                T dbValue = cacheAsideRequest.getDbSelector().get();
                log.info("cache miss, query db and the db value is: {}",dbValue);
                if (Objects.nonNull(dbValue)) {
                    // flag的含义：false：不进行缓存；true：进行缓存。
                    boolean flag;
                    // 解析List中的每一个对象的零值
                    if (dbValue instanceof List) flag = cacheStrategyEnum.analysisList((List<?>) dbValue);
                    // 解析单个对象的零值
                    else flag = cacheStrategyEnum.analysisCustomObject(dbValue);
                    if (flag) redisTemplate.opsForValue().set(
                            cacheKey,
                            JSONObject.toJSONString(dbValue),
                            expiredTime,
                            timeUnit
                    );
                }
                return dbValue;
            } else {
                T t = JSONObject.parseObject(cacheValueString, new TypeReference<T>() {});
                log.info("hit cache, the cache value is: {}",cacheValueString);
                return t;
            }
        } catch (Exception e) {
            log.info("query cache or db error!");
            return cacheAsideRequest.getDefaultValue();
        }
    }
}
