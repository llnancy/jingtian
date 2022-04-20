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
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
