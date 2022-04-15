package com.sunchaser.sparrow.middleware.zookeeper;

import com.google.common.collect.Maps;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 直接注册Watcher进行事件监听需要我们反复注册Watcher
 *
 * Apache Curator 提供了Cache来实现对Zookeeper服务端事件的监听
 *
 * 有三大类Cache：
 * NodeCache：对一个节点进行监听，包含节点数据的增删改和节点的创建与删除；
 * PathChildrenCache：对指定节点的一级子节点进行监听，只监听一级子节点的数据的增删改；
 * TreeCache：综合NodeCache和PathChildrenCache，对指定节点及其子节点进行监听，可设置监听的子节点的深度。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/8
 */
public class ApacheCuratorCacheListenerTest {

    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        client.start();

        final String path = "/user";

        /*=======================================NodeCache开始=======================================*/
        // 创建NodeCache，监听"/user"节点
        NodeCache nodeCache = new NodeCache(client, path);
        // start()方法有一个boolean类型参数，默认为false。
        // 当设置为true时，NodeCache在第一次启动的时候就会立即从Zookeeper上读取对应节点的数据内容，并保存在Cache中。
        nodeCache.start(true);

        ChildData currentData = nodeCache.getCurrentData();
        if (currentData != null) {
            System.out.println("NodeCache-[/user]节点初始化数据为：" + new String(currentData.getData()));
        } else {
            System.out.println("NodeCache-[/user]节点数据为空");
        }

        // 添加监听器
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = nodeCache.getCurrentData();
                String data = new String(childData.getData());
                System.out.println("NodeCache-[/user]节点路径：" + childData + "，节点数据为：" + data);
            }
        });
        /*=======================================NodeCache结束=======================================*/

        /*===================================PathChildrenCache开始===================================*/
        // 创建PathChildrenCache，监听"/user"节点
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        /**
         * org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode
         * 指定初始化模式：
         * NORMAL：普通异步初始化
         * BUILD_INITIAL_CACHE：同步初始化
         * POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
         */
        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

        List<ChildData> children = pathChildrenCache.getCurrentData();
        System.out.println("获取[/user]子节点列表：");
        children.forEach(childData -> {
            System.out.println(new String(childData.getData()));
        });
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println(LocalDateTime.now() + " " + event.getType());
                PathChildrenCacheEventProcessorEnum.getPathChildrenCacheEventProcessor(event.getType())
                        .process(client, event);
            }
        });
        /*===================================PathChildrenCache结束===================================*/

        /*=======================================TreeCache开始=======================================*/
        // 创建TreeCache，监听"/user"节点
        TreeCache treeCache = TreeCache.newBuilder(client, path)
                .setCacheData(false)
                .build();
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if (event.getData() != null) {
                    System.out.println("TreeCache,type=" + event.getType() + ",path=" + event.getData().getPath());
                } else {
                    System.out.println("TreeCache,type=" + event.getType());
                }
            }
        });
        treeCache.start();
        /*=======================================TreeCache结束=======================================*/

        System.in.read();
    }

    public enum PathChildrenCacheEventProcessorEnum {
        CHILD_ADDED_PROCESSOR(PathChildrenCacheEvent.Type.CHILD_ADDED) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                ChildData data = event.getData();
                System.out.println("PathChildrenCache添加子节点：" + data.getPath() + "，节点数据为：" + new String(data.getData()));
            }
        },
        CHILD_UPDATED_PROCESSOR(PathChildrenCacheEvent.Type.CHILD_UPDATED) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                ChildData data = event.getData();
                System.out.println("PathChildrenCache修改子节点路径：" + data.getPath() + "，修改子节点数据：" + new String(data.getData()));
            }
        },
        CHILD_REMOVED_PROCESSOR(PathChildrenCacheEvent.Type.CHILD_REMOVED) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                System.out.println("PathChildrenCache删除子节点：" + event.getData().getPath());
            }
        },
        CONNECTION_SUSPENDED_PROCESSOR(PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                System.out.println("PathChildrenCache监听到CONNECTION_SUSPENDED事件");
            }
        },
        CONNECTION_RECONNECTED_PROCESSOR(PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                System.out.println("PathChildrenCache监听到CONNECTION_RECONNECTED事件");
            }
        },
        CONNECTION_LOST_PROCESSOR(PathChildrenCacheEvent.Type.CONNECTION_LOST) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                System.out.println("PathChildrenCache监听到CONNECTION_LOST事件");
            }
        },
        INITIALIZED_PROCESSOR(PathChildrenCacheEvent.Type.INITIALIZED) {
            @Override
            void process(CuratorFramework client, PathChildrenCacheEvent event) {
                System.out.println("PathChildrenCache:子节点初始化成功...");
            }
        }
        ;
        private final PathChildrenCacheEvent.Type eventType;

        private static final Map<PathChildrenCacheEvent.Type, PathChildrenCacheEventProcessorEnum> enumMap = Maps.newHashMap();

        static {
            for (PathChildrenCacheEventProcessorEnum pathChildrenCacheEventProcessorEnum : PathChildrenCacheEventProcessorEnum.values()) {
                enumMap.put(pathChildrenCacheEventProcessorEnum.eventType, pathChildrenCacheEventProcessorEnum);
            }
        }

        public static PathChildrenCacheEventProcessorEnum getPathChildrenCacheEventProcessor(PathChildrenCacheEvent.Type eventType) {
            return enumMap.get(eventType);
        }

        PathChildrenCacheEventProcessorEnum(PathChildrenCacheEvent.Type eventType) {
            this.eventType = eventType;
        }

        public PathChildrenCacheEvent.Type getEventType() {
            return eventType;
        }

        abstract void process(CuratorFramework client, PathChildrenCacheEvent event);
    }
}
