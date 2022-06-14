package com.sunchaser.sparrow.middleware.mq.rabbitmq.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * topic exchange 主题类型交换机 消息生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            Map<String, String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("quick.orange.rabbit", "被队列Q1 Q2接收\n");
            bindingKeyMap.put("lazy.orange.elephant", "被队列Q1 Q2接收\n");
            bindingKeyMap.put("quick.orange.fox", "被队列Q1接收\n");
            bindingKeyMap.put("lazy.brown.fox", "被队列Q2接收\n");
            bindingKeyMap.put("lazy.pink.rabbit", "虽然满足两个绑定但只会被队列Q2接收一次\n");
            bindingKeyMap.put("quick.brown.fox", "没有队列接收，丢弃\n");
            bindingKeyMap.put("quick.orange.male.rabbit", "没有队列接收，丢弃\n");
            bindingKeyMap.put("lazy.orange.male.rabbit", "被队列Q2接收\n");
            for (Map.Entry<String, String> bindingKey : bindingKeyMap.entrySet()) {
                String routingKey = bindingKey.getKey();
                String message = bindingKey.getValue();
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
                System.out.println(" [x] Topic Exchange Sent '" + routingKey + "':" + message);
            }
        }
    }
}
