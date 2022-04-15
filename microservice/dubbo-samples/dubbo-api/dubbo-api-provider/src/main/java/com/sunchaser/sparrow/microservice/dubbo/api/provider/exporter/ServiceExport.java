package com.sunchaser.sparrow.microservice.dubbo.api.provider.exporter;

import com.sunchaser.sparrow.microservice.dubbo.api.provider.facade.impl.EchoFacadeImpl;
import com.sunchaser.sparrow.microservice.dubbo.facade.EchoFacade;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/26
 */
@Component
@DependsOn("echoFacadeImpl")
public class ServiceExport implements InitializingBean {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceConfig<EchoFacade> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig(applicationName));
        service.setRegistry(new RegistryConfig(zookeeperAddress));
        service.setInterface(EchoFacade.class);
        service.setRef(applicationContext.getBean(EchoFacadeImpl.class));
        service.export();
    }
}
