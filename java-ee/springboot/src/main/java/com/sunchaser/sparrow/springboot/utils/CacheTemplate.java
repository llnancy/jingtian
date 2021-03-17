package com.sunchaser.sparrow.springboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/27
 */
@Component
@Slf4j
public class CacheTemplate {
    private static final String CACHE_TEMPLATE_MEMENTO_PREFIX = "MEMENTO_";
    private static final ExecutorService mementoExecutor = Executors.newFixedThreadPool(5);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 缓存查询模板方法
     * @param key 缓存KEY
     * @param dbSelector db查询service
     * @param expire 缓存过期时间
     * @param <T> 查询结果泛型
     * @param defaultV 默认返回值
     * @param cacheStrategyEnum 对零值的缓存策略
     * @see com.sunchaser.sparrow.springboot.utils.CacheStrategyEnum
     * @return 最终查询结果
     */
    public <T> T selectCacheOrDb(String key,
                                 Supplier<T> dbSelector,
                                 int expire,
                                 T defaultV,
                                 CacheStrategyEnum cacheStrategyEnum) {
        try {
            String s = stringRedisTemplate.opsForValue().get(key);
            Gson gson = new Gson();
            // Object cacheValue = gson.fromJson(s, new TypeToken<T>() {}.getType());
            T cacheValue = JSONObject.parseObject(s, new TypeReference<T>() {});
//            T cacheValue = JSONObject.parseObject(s, new TypeReference<T>() {});
            log.info("CacheTemplate将缓存中的字符串解析成泛型的对象,key={},cacheValue={}", key, JSON.toJSONString(cacheValue));
            if (Objects.nonNull(cacheValue)) {
                // 命中缓存
                log.info("CacheTemplate的selectCacheOrDb返回值cacheValue={}", JSON.toJSONString(cacheValue));
                return cacheValue;
            }
            // 前面拼接MEMENTO_得到二级缓存（备忘录）的key
            String mementoKey = CACHE_TEMPLATE_MEMENTO_PREFIX + key;
            T dbValue;
            try {
                // 缓存为空，从底层数据源查询
                dbValue = dbSelector.get();
            } catch (Exception e) {
                // query error，发生异常进行降级
                return doDowngrade(key, mementoKey, expire, defaultV);
            }
            log.info("CacheTemplate从底层数据源查询数据,dbValue={}", dbValue);
            if (Objects.isNull(dbValue)) {
                // 底层数据源返回null指针，进行降级
                return doDowngrade(key, mementoKey, expire, defaultV);
            }
            // 底层数据源返回了对象，但可能全部为0，全部为0的时候不进行缓存，所以这里需要进行解析判断。
            // 解析dbValue中的值是否全部为零值（分为自定义对象和List<?>中的每一个自定义对象）
            // flag的含义：false：不进行缓存；true：进行缓存。
            boolean flag = dbValue instanceof List ? cacheStrategyEnum.analysisList((List<?>) dbValue) : cacheStrategyEnum.analysisCustomObject(dbValue);
            String dbValueString = JSON.toJSONString(dbValue);
            if (flag) {
                stringRedisTemplate.opsForValue().set(key, dbValueString, expire);
                log.info("CacheTemplate设置正确指标数据到缓存,key={},dbValue={},expire={}", key, dbValueString, expire);
                // 异步写入一个缓存时间较长的key（备忘录）
                asyncWriteMementoKey(mementoKey, dbValueString);
                return dbValue;
            }
            // 不进行缓存，数据"错误"，进行降级
            return doDowngrade(key, mementoKey, expire, dbValue);
        } catch (Exception e) {
            log.error("query-redis-error:", e);
            return defaultV;
        }
    }

    /**
     * 执行降级
     * @param key 一级缓存key
     * @param mementoKey 二级缓存key（备忘录key）
     * @param expire 过期时间
     * @param sourceValue 源value
     * @param <T> metrics
     * @return 降级后的value
     */
    private <T> T doDowngrade(String key, String mementoKey, int expire, T sourceValue) {
        T mementoCacheValue = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(mementoKey), new TypeReference<T>(){});
        log.info("CacheTemplate查询底层数据源异常,从备忘录缓存中恢复,mementoKey={},mementoCacheValue={}",mementoKey,JSON.toJSONString(mementoCacheValue));
        if (Objects.isNull(mementoCacheValue)) {
            // 备忘录为空，返回源底层指标
            return sourceValue;
        } else {
            // 用mementoCacheValue覆盖cacheValue
            String mementoCacheString = JSON.toJSONString(mementoCacheValue);
            int newExpire = expire / 2;
            stringRedisTemplate.opsForValue().set(key, mementoCacheString, newExpire);
            log.info("CacheTemplate用mementoCacheValue覆盖cacheValue,key={},dbValue={},expire={}", key, mementoCacheString, newExpire);
            return mementoCacheValue;
        }
    }

    /**
     * 异步写备忘录
     * @param mementoKey 备忘录的key
     * @param dbValueString 正确的指标
     */
    private void asyncWriteMementoKey(String mementoKey, String dbValueString) {
        mementoExecutor.execute(() -> {
            int expire = 3600 + ThreadLocalRandom.current().nextInt(3600);
            stringRedisTemplate.opsForValue().set(mementoKey, dbValueString, expire);
            log.info("CacheTemplate更新正确指标数据到备忘录缓存,mementoKey={},dbValue={},expire={}", mementoKey, dbValueString, expire);
        });
    }
}
