package com.sunchaser.sparrow.designpatterns.common.model.request;

import com.sunchaser.sparrow.designpatterns.common.enums.CacheStrategyEnum;
import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存备用模式请求入参
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/3
 */
@Data
public class CacheAsideRequest<T> {

    /**
     * 缓存key
     */
    private String cacheKey;

    /**
     * 缓存过期时间
     */
    private Integer expiredTime;

    /**
     * 过期时间的单位
     */
    private TimeUnit timeUnit;

    /**
     * DB查询器
     */
    private Supplier<T> dbSelector;

    /**
     * 发生异常进行降级的默认值
     */
    private T defaultValue;

    /**
     * 缓存数据的策略
     */
    private CacheStrategyEnum cacheStrategyEnum;
}
