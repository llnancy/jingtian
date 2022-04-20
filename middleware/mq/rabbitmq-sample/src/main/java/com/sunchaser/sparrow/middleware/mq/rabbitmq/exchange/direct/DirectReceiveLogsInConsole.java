package com.sunchaser.sparrow.middleware.mq.rabbitmq.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

/**
 * direct exchange 直接类型交换机 消息消费者：消费info和warn日志消息后在控制台进行打印
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class DirectReceiveLogsInConsole {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 定义队列
        String queueName = "console";
        channel.queueDeclare(queueName, false, false, false, null);
        // 交换机绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        channel.queueBind(queueName, EXCHANGE_NAME, "warn");
        System.out.println(" [*] Waiting for messages. Print info and warn log in console. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
