package io.github.llnancy.middleware.mq.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import io.github.llnancy.middleware.mq.rabbitmq.common.RabbitMqHelper;

import java.util.Scanner;

/**
 * 工作队列模式 消息生产者
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/18
 */
public class NewTask {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqHelper.getChannel()) {
            /*
             * 声明一个队列用来发送消息
             *
             * @param queue      队列名称
             * @param durable    消息是否进行持久化（不持久化则保存在内存中）。true：进行持久化
             * @param exclusive  是否声明为独占队列。当前队列只允许当前连接使用，其它连接不可用。true：为独占队列
             * @param autoDelete 是否在消费完成后自动删除队列。true：自动删除
             * @param arguments  队列的其它参数信息
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 从控制台读取消息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();

                /*
                 * 往信道中发送一个消息
                 *
                 * @param exchange   交换机名称
                 * @param routingKey 路由 key
                 * @param props      消息的其它参数信息
                 * @param body       消息体
                 */
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
