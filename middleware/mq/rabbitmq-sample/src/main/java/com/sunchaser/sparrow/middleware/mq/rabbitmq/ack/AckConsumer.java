package com.sunchaser.sparrow.middleware.mq.rabbitmq.ack;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

/**
 * 手动应答 消息消费者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class AckConsumer {
    private static final String ACK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                Thread.sleep(1000);
                // Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int i = 1 / 0;

            System.out.println(" [x] Received '" + new String(message.getBody()) + "'");
            /*
             * 手动应答
             *
             * @param deliveryTag 消息标记tag
             * @param multiple    是否批量应答：false：不批量；true：批量。
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费时的回调逻辑");
        };
        // 设置autoAck = false开启手动应答
        channel.basicConsume(ACK_QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
