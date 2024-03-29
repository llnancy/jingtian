package io.github.llnancy.middleware.mq.rabbitmq.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.github.llnancy.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.util.Scanner;

/**
 * direct exchange 直接类型交换机 消息生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String next = scanner.next();
                // 消息内容和消息的路由键之间用冒号:进行分隔
                String[] split = next.split(":");
                String message = split[0];
                String routingKey = split[1];
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
                System.out.println(" [x] Direct Exchange Sent '" + routingKey + "':'" + message + "'");
            }
        }
    }
}
