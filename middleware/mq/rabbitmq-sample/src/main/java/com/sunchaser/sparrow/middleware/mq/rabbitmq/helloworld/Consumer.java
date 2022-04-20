package com.sunchaser.sparrow.middleware.mq.rabbitmq.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 简单队列模式 消费者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/15
 */
public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        // 可不设置，username和password默认都为guest
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 消费者不需要使用try-with-resources语法自动关闭Connection和Channel。
        // 因为消息的发送是异步的，有可能消费者先启动，如果连接和信道都关闭了，则无法进行消费消息
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        /*
         * 消息传递（消费）时的回调
         */
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(" [x] Received '" + new String(message.getBody()) + "'");
        };

        /*
         * 取消消费时的回调
         */
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费时的回调逻辑");
        };

        /*
         * 消费者消费消息
         *
         * @param queue           队列名称
         * @param autoAck         消费成功之后是否自动回应ack。true：自动；false：手动。
         * @param deliverCallback 消息传递时的回调
         * @param cancelCallback  消费者取消消费时的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
