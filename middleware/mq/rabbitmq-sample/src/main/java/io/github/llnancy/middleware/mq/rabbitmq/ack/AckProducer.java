package io.github.llnancy.middleware.mq.rabbitmq.ack;

import com.rabbitmq.client.Channel;
import io.github.llnancy.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.util.Scanner;

/**
 * 手动应答 消息生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class AckProducer {

    private static final String ACK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            channel.queueDeclare(ACK_QUEUE_NAME, false, false, false, null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                channel.basicPublish("", ACK_QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] ack_queue Sent '" + message + "'");
            }
        }
    }
}
