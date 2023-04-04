package io.github.llnancy.middleware.mq.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 简单队列模式 生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/15
 */
public class Producer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 host
        factory.setHost("127.0.0.1");
        // 端口号。默认值 5672，可不进行设置。
        factory.setPort(5672);
        // 虚拟主机名。可不进行设置，默认值为 "/"。
        factory.setVirtualHost("/llnancy");
        // 可不设置，username 和 password 默认都为 guest
        factory.setUsername("guest");
        factory.setPassword("guest");

        // Connection 和 Channel 都实现了 java.io.Closeable 接口
        // 生产者中可使用 try-with-resources 语法
        try (// 创建连接
             Connection connection = factory.newConnection();
             // 创建信道
             Channel channel = connection.createChannel()) {

            /*
             * 声明一个队列用来发送消息
             *
             * @param queue      队列名称
             * @param durable    消息是否进行持久化（不持久化则保存在内存中）。true：进行持久化
             * @param exclusive  是否声明为独占队列。当前队列只允许当前连接使用，其它连接不可用。true：为独占队列
             * @param autoDelete 是否在消费完成后自动删除队列。true：自动删除
             * @param arguments  队列的其它参数信息
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";

            /*
             * 往信道中发送一个消息
             *
             * @param exchange   交换机名称
             * @param routingKey 路由 key
             * @param props      消息的其它参数信息
             * @param body       消息体
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
