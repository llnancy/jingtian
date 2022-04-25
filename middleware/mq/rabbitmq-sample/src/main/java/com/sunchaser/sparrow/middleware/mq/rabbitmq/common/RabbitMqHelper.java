package com.sunchaser.sparrow.middleware.mq.rabbitmq.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * rabbitmq 工具类
 * 抽取一些公共方法
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/15
 */
public class RabbitMqHelper {

    public static Channel getChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置host
        factory.setHost("127.0.0.1");
        // 端口号。默认值5672，可不进行设置。
        factory.setPort(5672);
        // 虚拟主机名。可不进行设置，默认值为"/"。
        factory.setVirtualHost("/");
        // 可不设置，username和password默认都为guest
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
