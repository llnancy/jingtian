package com.sunchaser.model.response;

/**
 * @author: lilu
 * @date: 2019/9/6
 * @description: HTTP请求统一响应包含data数据域和数据总数的实体。供需要分页查询的HTTP请求使用。
 */
public class HttpResponsePageData<T> {

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
     * 数据总记录数，前端可根据总记录数算出总页数。
     */
    private Integer count;

    /* getter and setter */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "HttpResponsePageData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", count=" + count +
                '}';
    }
}
