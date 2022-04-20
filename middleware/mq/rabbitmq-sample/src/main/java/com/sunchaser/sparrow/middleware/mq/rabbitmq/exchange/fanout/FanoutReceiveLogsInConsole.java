package com.sunchaser.sparrow.middleware.mq.rabbitmq.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

/**
 * fanout exchange 扇出（发布订阅）类型交换机 消息消费者：消费日志消息后在控制台进行打印
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class FanoutReceiveLogsInConsole {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 临时队列：具有随机生成名称的非持久、独占、自动删除的队列。
        String queueName = channel.queueDeclare().getQueue();
        // 交换机绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. Print message in console. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
