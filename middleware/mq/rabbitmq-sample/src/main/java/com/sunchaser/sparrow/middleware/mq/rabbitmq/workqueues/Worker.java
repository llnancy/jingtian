package com.sunchaser.sparrow.middleware.mq.rabbitmq.workqueues;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

/**
 * 工作队列模式 消息消费者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/15
 */
public class Worker {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();

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

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
