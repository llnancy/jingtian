package com.sunchaser.sparrow.middleware.mq.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.MessageProperties;
import com.sunchaser.sparrow.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 消息的发布确认 生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class ConfirmProducer {
    private static final String QUEUE_NAME = "confirm_queue";
    private static final int COUNT = 1000;

    public static void main(String[] args) throws Exception {
        // singlePublishConfirmMessage();// 单个发布确认1000条消息耗时：1130ms
        // batchPublishConfirmMessage();// 批量发布确认1000条消息耗时：110ms
        asyncPublishConfirmMessage();// 异步发布确认1000条消息耗时81ms
    }

    /**
     * 消息单个发布确认
     * 同步，耗时久。
     */
    private static void singlePublishConfirmMessage() throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 开启发布确认
            channel.confirmSelect();
            long begin = System.currentTimeMillis();
            for (int i = 0; i < COUNT; i++) {
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, String.valueOf(i).getBytes());
                // 等待回传确认ack
                boolean waitForConfirms = channel.waitForConfirms();
                if (waitForConfirms) {
                    System.out.println("第" + i + "条消息单个发布确认成功");
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("单个发布确认" + COUNT + "条消息耗时：" + (end - begin) + "ms");
        }
    }

    /**
     * 消息批量发布确认
     * 批量同步，耗时减少，但出现问题时无法判断是哪条消息出错。
     */
    private static void batchPublishConfirmMessage() throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.confirmSelect();
            long begin = System.currentTimeMillis();
            int batchSize = 100;
            for (int i = 0; i < COUNT; i++) {
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, String.valueOf(i).getBytes());
                // 每发布batchSize条消息确认一次
                if ((i + 1) % batchSize == 0) {
                    boolean waitForConfirms = channel.waitForConfirms();
                    if (waitForConfirms) {
                        System.out.println("第" + (i + 1) + "条消息批量发布确认成功");
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("批量发布确认" + COUNT + "条消息耗时：" + (end - begin) + "ms");
        }
    }

    /**
     * 消息异步发布确认
     * 最佳性能和资源使用，监听器异步回调，出错时能定位到具体消息。推荐使用。
     */
    private static void asyncPublishConfirmMessage() throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.confirmSelect();
            long begin = System.currentTimeMillis();
            // 消息容器（跳表） ====> k：消息发送序号；v：消息体
            ConcurrentSkipListMap<Long, String> confirmMap = new ConcurrentSkipListMap<>();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    // 删除确认的消息
                    if (multiple) {// 批量ack
                        ConcurrentNavigableMap<Long, String> headMap = confirmMap.headMap(deliveryTag);
                        headMap.clear();
                    } else {
                        confirmMap.remove(deliveryTag);
                    }
                    System.out.println("确认的消息deliveryTag：" + deliveryTag);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    String message = confirmMap.get(deliveryTag);
                    System.out.println("未确认的消息的deliveryTag：" + deliveryTag + "；消息体为：" + message);
                }
            });

            for (int i = 0; i < COUNT; i++) {
                String message = String.valueOf(i);
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                // 将被指派的唯一ID和消息内容进行映射，并存储在跳表中
                confirmMap.put(channel.getNextPublishSeqNo(), message);
            }

            long end = System.currentTimeMillis();
            System.out.println("异步发布确认" + COUNT + "条消息耗时" + (end - begin) + "ms");
        }
    }
}
