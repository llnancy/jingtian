package com.sunchaser.microservice.rpc.registry.impl;

import com.sunchaser.microservice.rpc.registry.Registry;
import com.sunchaser.microservice.rpc.registry.ServiceInfo;
import com.sunchaser.microservice.rpc.test.HelloService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class ZookeeperRegistry implements Registry<ServiceInfo> {

    private final InstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
    private ServiceDiscovery<ServiceInfo> serviceDiscovery;
    private ServiceCache<ServiceInfo> serviceCache;

    public void start() throws Exception {
        String address = "127.0.0.1:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        client.start();
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .client(client)
                .basePath("/sunchaser/rpc")
                .serializer(serializer)
                .build();
        serviceDiscovery.start();
        serviceCache = serviceDiscovery.serviceCacheBuilder()
                .name(HelloService.class.getName())
                .build();
        client.blockUntilConnected();
        serviceDiscovery.start();
        serviceCache.start();
    }


    @Override
    public void register(ServiceInstance<ServiceInfo> service) throws Exception {
        serviceDiscovery.registerService(service);
    }

    @Override
    public void unregister(ServiceInstance<ServiceInfo> service) throws Exception {
        serviceDiscovery.unregisterService(service);
    }

    @Override
    public List<ServiceInstance<ServiceInfo>> query(String name) {
        return serviceCache.getInstances()
                .stream()
                .filter(s -> s.getName().equals(name))
                .collect(Collectors.toList());
    }
}
