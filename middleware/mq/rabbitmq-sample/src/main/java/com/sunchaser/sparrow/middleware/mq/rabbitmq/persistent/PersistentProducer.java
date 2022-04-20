package com.sunchaser.sparrow.middleware.mq.rabbitmq.persistent;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.util.Scanner;

/**
 * 持久化消息和队列 生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class PersistentProducer {
    private static final String PERSISTENT_QUEUE_NAME = "persistent_queue";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            // durable：true（持久化队列）
            channel.queueDeclare(PERSISTENT_QUEUE_NAME, true, false, false, null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                // MessageProperties.PERSISTENT_TEXT_PLAIN：
                channel.basicPublish("", PERSISTENT_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] persistent_queue Sent '" + message + "'");
            }
        }
    }
}
