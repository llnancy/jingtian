package com.sunchaser.sparrow.springboot.utils;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 缓存模板类中使用的零值缓存策略枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/02/23
 */
@Getter
public enum CacheStrategyEnum {
    ALL_NO_ZERO("ALL_NO_ZERO","全部字段不为零则进行缓存") {
        @Override
        public boolean analysisList(List<?> list) throws IllegalAccessException {
            if (CollectionUtils.isEmpty(list)) return false;
            for (Object el : list) {
                // 获取包含父类super的所有字段
                Field[] allFields = getAllFields(el);
                for (Field field : allFields) {
                    field.setAccessible(true);
                    // 只要有一个字段为零，返回false
                    if (analysisZero(field.get(el))) return false;
                }
            }
            return true;
        }

        @Override
        public boolean analysisCustomObject(Object object) throws IllegalAccessException {
            if (Objects.isNull(object)) return false;
            Field[] allFields = getAllFields(object);
            for (Field field : allFields) {
                field.setAccessible(true);
                // 只要有一个字段为零，返回false
                if (analysisZero(field.get(object))) return false;
            }
            return true;
        }
    },
    ANY_NO_ZERO("ANY_NO_ZERO","任意一个字段不为零则进行缓存") {
        @Override
        public boolean analysisList(List<?> list) throws IllegalAccessException {
            for (Object el : list) {
                // 获取包含父类super的所有字段
                Field[] allFields = getAllFields(el);
                for (Field field : allFields) {
                    field.setAccessible(true);
                    // 任意一个字段不为零，返回true
                    if (!analysisZero(field.get(el))) return true;
                }
            }
            return false;
        }

        @Override
        public boolean analysisCustomObject(Object object) throws IllegalAccessException {
            Field[] allFields = getAllFields(object);
            for (Field field : allFields) {
                field.setAccessible(true);
                // 任意一个字段不为零，返回true
                if (!analysisZero(field.get(object))) return true;
            }
            return false;
        }
    };

    /**
     * 缓存策略
     */
    private final String strategy;

    /**
     * 描述
     */
    private final String desc;

    /**
     * flyweight
     */
    private static final Map<String,CacheStrategyEnum> enumMap = Maps.newHashMap();

    static {
        for (CacheStrategyEnum cacheStrategyEnum : CacheStrategyEnum.values())
            enumMap.put(cacheStrategyEnum.strategy,cacheStrategyEnum);
    }

    CacheStrategyEnum(String strategy, String desc) {
        this.strategy = strategy;
        this.desc = desc;
    }

    /**
     * 抽象方法：解析List中的自定义对象
     * @param list list对象
     * @return false：不进行缓存；true：进行缓存
     * @throws IllegalAccessException 上层进行异常处理
     */
    public abstract boolean analysisList(List<?> list) throws IllegalAccessException ;

    /**
     * 抽象方法：解析自定义对象
     * @param object 待解析的自定义对象
     * @return false：不进行缓存；true：进行缓存
     * @throws IllegalAccessException 上层进行异常处理
     */
    public abstract boolean analysisCustomObject(Object object) throws IllegalAccessException ;

    /**
     * 解析字段是否为零值
     * @param o 待解析字段
     * @return true：为零值；false：不为零。
     */
    private static boolean analysisZero(Object o) {
        if (o instanceof Long) {
            return (Long) o == 0L;
        } else if (o instanceof Integer) {
            return (Integer) o == 0;
        } else if (o instanceof Double) {
            return (Double) o == 0.00;
        }
        return false;
    }

    /**
     * 获取包含super父类的所有字段
     * @param object 需要获取字段的对象
     * @return 对象的所有字段
     */
    private static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
