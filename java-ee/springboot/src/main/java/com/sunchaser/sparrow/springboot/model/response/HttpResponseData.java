package com.sunchaser.sparrow.springboot.model.response;

/**
 * @author lilu
 * @date 2019/9/4
 * @description HTTP请求统一响应包含data数据域的实体。供需要返回数据的HTTP请求使用。
 */
public class HttpResponseData<T> {

    /**
     * 响应code码
     */
    private String code;

    /**
     * 响应msg信息
     */
    private String msg;

    /**
     * 响应数据域
     */
    private T data;

    /**
     * 成功时调用
     * @param httpResponseCodeMsg 返回码对象
     * @param t 数据域对象
     * @return HttpResponseData对象
     */
    public static <T> HttpResponseData<T> success(HttpResponseCodeMsg httpResponseCodeMsg, T t) {
        return new HttpResponseData<>(httpResponseCodeMsg.getCode(),httpResponseCodeMsg.getMsg(),t);
    }

    /**
     * 失败时调用，失败时数据域为null。
     * @param httpResponseCodeMsg 返回码对象
     * @return HttpResponseData对象
     */
    public static <T> HttpResponseData<T> error(HttpResponseCodeMsg httpResponseCodeMsg) {
        return new HttpResponseData<>(httpResponseCodeMsg.getCode(),httpResponseCodeMsg.getMsg(),null);
    }

    /**
     * 无参构造
     */
    public HttpResponseData() {
    }

    /**
     * 全参构造
     * @param code
     * @param msg
     * @param data
     */
    public HttpResponseData(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /* getter and setter */
    public String getCode() {
        return code;
    }

    public HttpResponseData<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public HttpResponseData<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public HttpResponseData<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "HttpResponseData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
