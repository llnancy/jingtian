package com.sunchaser.sparrow.middleware.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Apache Curator组件同步API的使用
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/1
 */
public class ApacheCuratorSyncTest {
    public static void main(String[] args) throws Exception {
        // zk地址
        String zkAddress = "127.0.0.1:2181";
        // 重试策略：如果连接不上zk集群，重试3次，重试时间间隔递增
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 创建CuratorFramework客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        // 启动
        client.start();

        final String path = "/user";
        // 创建持久节点
        String createdPath = client.create()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, "sunchaser".getBytes());
        System.out.println("createdPath: " + createdPath);

        // 检查一个节点是否存在
        Stat stat = client.checkExists().forPath(path);
        System.out.println("path exist?: " + (stat != null));

        // 获取节点存储的数据
        byte[] data = client.getData().forPath(path);
        System.out.println("data: " + new String(data));

        // 在/user目录下，创建多个临时顺序子节点
        for (int i = 0; i < 3; i++) {
            client.create()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(path + "/child-" + i);
        }

        // 获取所有子节点
        List<String> childrenNodes = client.getChildren().forPath(path);
        System.out.println("childrenNodes：" + childrenNodes);

        // delete()：删除指定节点
        // deletingChildrenIfNeeded()：级联删除子节点
        client.delete()
                .deletingChildrenIfNeeded()
                .forPath(path);
    }
}
