package com.sunchaser.sparrow.middleware.mq.rabbitmq.exchange.direct;

import cn.hutool.core.io.FileUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.io.File;

/**
 * direct exchange 直接类型交换机 消息消费者：消费error日志消息后将消息内容写入文件
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class DirectReceiveLogsInFile {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqHelper.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 临时队列：具有随机生成名称的非持久、独占、自动删除的队列。
        String queueName = channel.queueDeclare().getQueue();
        // 交换机绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "error");
        System.out.println(" [*] Waiting for messages. Write error log to file. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            File file = new File("/Users/sunchaser/workspace/idea-projects/sunchaser-sparrow/middleware/mq/rabbitmq-sample/src/main/resources/direct_log.txt");
            FileUtil.appendUtf8String(message, file);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
