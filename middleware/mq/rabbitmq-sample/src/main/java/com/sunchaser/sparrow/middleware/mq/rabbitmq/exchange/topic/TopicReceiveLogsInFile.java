package com.sunchaser.sparrow.middleware.mq.rabbitmq.exchange.topic;

import cn.hutool.core.io.FileUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.io.File;

/**
 * topic exchange 直接类型交换机 消息消费者：绑定队列Q2
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class TopicReceiveLogsInFile {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 队列Q1
        String queueName = "Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
        System.out.println(" [*] " + queueName + " Waiting for messages. Write *.*.rabbit and lazy.# log to file. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            File file = new File("/Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/middleware/mq/rabbitmq-sample/src/main/resources/topic_log.txt");
            FileUtil.appendUtf8String(message, file);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':" + message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
