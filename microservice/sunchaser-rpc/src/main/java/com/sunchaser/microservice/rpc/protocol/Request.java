package com.sunchaser.microservice.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
@Data
public class Request implements Serializable {

    private static final long serialVersionUID = 4851167785488839231L;

    private String serviceName;

    private String methodName;

    private Class<?>[] argTypes;

    private Object[] args;
}
