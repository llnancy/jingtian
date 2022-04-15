package com.sunchaser.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 9118607741146835217L;
    private Integer code;
    private String msg;
    private Object data;
}
