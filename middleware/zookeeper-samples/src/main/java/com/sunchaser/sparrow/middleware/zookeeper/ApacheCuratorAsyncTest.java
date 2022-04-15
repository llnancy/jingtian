package com.sunchaser.sparrow.middleware.zookeeper;

import com.google.common.collect.Maps;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Map;

/**
 * Apache Curator组件异步API的使用
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/1
 */
public class ApacheCuratorAsyncTest {
    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        client.start();

        /**
        // 添加监听器
        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                processEvent(client, event);
            }
        });
        **/

        // JDK8
        client.getCuratorListenable().addListener(ApacheCuratorAsyncTest::processEvent);

        final String path = "/user";

        // async invoke

        // 创建持久节点
        String createdPath = client.create()
                .withMode(CreateMode.PERSISTENT)
                .inBackground()
                .forPath(path, "async-sunchaser".getBytes());
        // System.out.println("createdPath: " + createdPath);

        // 检查节点是否存在
        Stat stat = client.checkExists()
                .inBackground()
                .forPath(path);
        // System.out.println("path exist?: " + (stat != null));

        // 获取节点存储的数据
        byte[] data = client.getData()
                .inBackground()
                .forPath(path);
        // System.out.println("data: " + new String(data));

        // 在/user目录下，创建多个临时顺序子节点
        for (int i = 0; i < 3; i++) {
            client.create()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .inBackground()
                    .forPath(path + "/child-" + i);
        }

        // 获取所有子节点
        List<String> childrenNodes = client.getChildren()
                .inBackground()
                .forPath(path);
        // System.out.println("childrenNodes：" + childrenNodes);

        // 添加BackgroundCallback
        client.getChildren().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("in background: " + event.getType() + "," + event.getPath());
            }
        }).forPath(path);

        // delete()：删除指定节点
        // deletingChildrenIfNeeded()：级联删除子节点
        client.delete()
                .deletingChildrenIfNeeded()
                .inBackground()
                .forPath(path);

        System.in.read();
    }

    private static void processEvent(CuratorFramework client, CuratorEvent event) {
        CuratorEventProcessorEnum.getCuratorEventProcessor(event.getType()).process(client, event);
    }

    public enum CuratorEventProcessorEnum {
        CREATE_PROCESSOR(CuratorEventType.CREATE) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process create event: " + event.getPath());
            }
        },

        DELETE_PROCESSOR(CuratorEventType.DELETE) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process delete event: " + event.getPath());
            }
        },

        EXISTS_PROCESSOR(CuratorEventType.EXISTS) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process exists event: " + event.getPath());
            }
        },

        GET_DATA_PROCESSOR(CuratorEventType.GET_DATA) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process get data event: " + event.getPath());
            }
        },

        SET_DATA_PROCESSOR(CuratorEventType.SET_DATA) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process set data event: " + event.getPath());
            }
        },

        CHILDREN_PROCESSOR(CuratorEventType.CHILDREN) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process children event: " + event.getPath());
            }
        },

        SYNC_PROCESSOR(CuratorEventType.SYNC) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process sync event: " + event.getPath());
            }
        },

        GET_ACL_PROCESSOR(CuratorEventType.GET_ACL) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process get acl event: " + event.getPath());
            }
        },

        SET_ACL_PROCESSOR(CuratorEventType.SET_ACL) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process set acl event: " + event.getPath());
            }
        },

        TRANSACTION_PROCESSOR(CuratorEventType.TRANSACTION) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process transaction event: " + event.getPath());
            }
        },

        GET_CONFIG_PROCESSOR(CuratorEventType.GET_CONFIG) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process get config event: " + event.getPath());
            }
        },

        RECONFIG_PROCESSOR(CuratorEventType.RECONFIG) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process reconfig event: " + event.getPath());
            }
        },

        WATCHED_PROCESSOR(CuratorEventType.WATCHED) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process watched event: " + event.getPath());
            }
        },

        REMOVE_WATCHES_PROCESSOR(CuratorEventType.REMOVE_WATCHES) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process remove watches event: " + event.getPath());
            }
        },

        CLOSING_PROCESSOR(CuratorEventType.CLOSING) {
            @Override
            void process(CuratorFramework client, CuratorEvent event) {
                System.out.println("process closing event: " + event.getPath());
            }
        }
        ;
        private final CuratorEventType curatorEventType;

        private static final Map<CuratorEventType, CuratorEventProcessorEnum> enumMap = Maps.newHashMap();

        static {
            for (CuratorEventProcessorEnum value : CuratorEventProcessorEnum.values())
                enumMap.put(value.curatorEventType, value);
        }

        public static CuratorEventProcessorEnum getCuratorEventProcessor(CuratorEventType curatorEventType) {
            return enumMap.get(curatorEventType);
        }

        CuratorEventProcessorEnum(CuratorEventType curatorEventType) {
            this.curatorEventType = curatorEventType;
        }

        public CuratorEventType getCuratorEventType() {
            return curatorEventType;
        }

        abstract void process(CuratorFramework client, CuratorEvent event);
    }
}
