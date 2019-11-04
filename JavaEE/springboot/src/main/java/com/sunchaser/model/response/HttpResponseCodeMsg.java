package com.sunchaser.model.response;

/**
 * @author: lilu
 * @date: 2019/9/4
 * @description: HTTP请求统一响应不包含data数据域的实体，提供通用状态码。
 */
public class HttpResponseCodeMsg {

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String msg;

    /* 通用返回状态码 */
    public static HttpResponseCodeMsg SUCCESS = new HttpResponseCodeMsg("10000","操作成功");
    public static HttpResponseCodeMsg FAIL = new HttpResponseCodeMsg("20000","操作失败");
    public static HttpResponseCodeMsg PARAM_NOT_VALID = new HttpResponseCodeMsg("20000","参数不合法");

    /**
     * 无参构造
     */
    private HttpResponseCodeMsg() {
    }

    /**
     * 全参构造
     * @param code 状态码
     * @param msg 状态描述
     */
    private HttpResponseCodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /* getter and setter */
    public String getCode() {
        return code;
    }

    public HttpResponseCodeMsg setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public HttpResponseCodeMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "HttpResponseCodeMsg{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
