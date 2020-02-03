package com.sunchaser.springboot.exception;

import com.sunchaser.springboot.model.response.HttpResponseCodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lilu
 * @date 2019/9/16
 * @description 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理方法的参数验证失败的异常
     * @param e MethodArgumentNotValidException 参数验证失败异常
     * @return 统一响应状态码
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public HttpResponseCodeMsg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error("[the method parameters are not valid], message={}", e.getBindingResult().getFieldError().getDefaultMessage());
        return HttpResponseCodeMsg.PARAM_NOT_VALID;
    }

    /**
     * 处理控制器方法缺少必要参数的异常
     * @param e MissingServletRequestParameterException 请求缺少必要参数异常
     * @return 统一响应状态码
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public HttpResponseCodeMsg handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LOGGER.error("[the api missing required parameter], message={}",e.getMessage());
        return HttpResponseCodeMsg.PARAM_NOT_VALID;
    }
}
