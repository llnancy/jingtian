package com.sunchaser.sparrow.middleware.mq.rabbitmq.qos;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

/**
 * 不公平分发 消费者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class QosConsumer {
    private static final String PERSISTENT_QUEUE_NAME = "persistent_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                Thread.sleep(1000);
                // Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" [x] Received persistent_queue '" + new String(message.getBody()) + "'");

            // 设置不公平分发
            channel.basicQos(1);

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
        channel.basicConsume(PERSISTENT_QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
