package com.sunchaser.microservice.rpc.registry;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public interface Registry<T> {

    void register(ServiceInstance<T> service) throws Exception;

    void unregister(ServiceInstance<T> service) throws Exception;

    List<ServiceInstance<T>> query(String name);
}
