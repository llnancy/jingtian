package com.sunchaser.sparrow.middleware.mq.rabbitmq.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.util.Scanner;

/**
 * fanout exchange 扇出（发布订阅）类型交换机 消息生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            // 定义fanout类型的交换机
            // channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                System.out.println(" [x] Fanout Exchange Sent '" + message + "'");
            }
        }
    }
}
