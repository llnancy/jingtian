package com.sunchaser.sparrow.javaee.graphql.exceptions;

import graphql.GraphQLException;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author sunchaser admin@lilu.org.cn
 * @see graphql.kickstart.execution.error.DefaultGraphQLErrorHandler
 * @since JDK8 2022/5/6
 */
@Component
public class GraphQLExceptionHandler {

    @ExceptionHandler({GraphQLException.class, ConstraintViolationException.class})
    public ThrowableGraphQLError handle(Exception e) {
        return new ThrowableGraphQLError(e);
    }

    @ExceptionHandler(RuntimeException.class)
    public ThrowableGraphQLError handle(RuntimeException e) {
        return new ThrowableGraphQLError(e, "Internal Server Error");
    }
}
