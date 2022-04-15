package com.sunchaser.sparrow.mybatisgenerate.config;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.springframework.stereotype.Service;

/**
 * 指定mysql中的tinyInt类型映射成java.lang.Integer类
 * @author sunchaser
 * @since JDK8 2019/10/28
 */
@Service
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    public MyJavaTypeResolver() {
        super();
        super.typeMap.put(-6,new JavaTypeResolverDefaultImpl.JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
