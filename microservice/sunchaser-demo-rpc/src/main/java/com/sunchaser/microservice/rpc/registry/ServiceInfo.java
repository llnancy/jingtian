package com.sunchaser.microservice.rpc.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = -6060448742023466377L;

    private String host;

    private int port;
}
