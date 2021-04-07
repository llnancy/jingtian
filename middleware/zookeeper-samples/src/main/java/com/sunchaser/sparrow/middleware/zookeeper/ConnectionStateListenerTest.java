package com.sunchaser.sparrow.middleware.zookeeper;

import com.google.common.collect.Maps;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.IOException;
import java.util.Map;

/**
 * Apache Curator监听连接状态的监听器：org.apache.curator.framework.state.ConnectionStateListener
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/2
 */
public class ConnectionStateListenerTest {
    public static void main(String[] args) throws IOException {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        client.start();

        /**
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                processStateChanged(client, newState);
            }
        });
        **/

        // JDK8
        client.getConnectionStateListenable().addListener(ConnectionStateListenerTest::processStateChanged);
        System.in.read();
    }

    private static void processStateChanged(CuratorFramework client, ConnectionState newState) {
        StateChangedProcessorEnum.getStateChangedProcessor(newState).process(client, newState);
    }

    public enum StateChangedProcessorEnum {
        CONNECTED_PROCESSOR(ConnectionState.CONNECTED) {
            @Override
            void process(CuratorFramework client, ConnectionState newState) {
                // 第一次成功连接到zookeeper后会进入该状态。
                // 对于每个CuratorFramework对象，此状态仅出现一次
                System.out.println("new state: " + newState);
            }
        },

        SUSPENDED_PROCESSOR(ConnectionState.SUSPENDED) {
            @Override
            void process(CuratorFramework client, ConnectionState newState) {
                // ZooKeeper的连接丢失
                System.out.println("new state: " + newState);
            }
        },

        RECONNECTED_PROCESSOR(ConnectionState.RECONNECTED) {
            @Override
            void process(CuratorFramework client, ConnectionState newState) {
                // 丢失的连接被重新建立
                System.out.println("new state: " + newState);
            }
        },

        LOST_PROCESSOR(ConnectionState.LOST) {
            @Override
            void process(CuratorFramework client, ConnectionState newState) {
                // 当Curator认为会话已经过期时，进入此状态
                System.out.println("new state: " + newState);
            }
        },

        READ_ONLY_PROCESSOR(ConnectionState.READ_ONLY) {
            @Override
            void process(CuratorFramework client, ConnectionState newState) {
                // 进入只读模式
                System.out.println("new state: " + newState);
            }
        }
        ;
        private final ConnectionState connectionState;

        private static final Map<ConnectionState, StateChangedProcessorEnum> enumMap = Maps.newHashMap();

        static {
            for (StateChangedProcessorEnum processorEnum : StateChangedProcessorEnum.values())
                enumMap.put(processorEnum.connectionState, processorEnum);
        }

        public static StateChangedProcessorEnum getStateChangedProcessor(ConnectionState connectionState) {
            return enumMap.get(connectionState);
        }

        StateChangedProcessorEnum(ConnectionState connectionState) {
            this.connectionState = connectionState;
        }

        public ConnectionState getConnectionState() {
            return connectionState;
        }

        abstract void process(CuratorFramework client, ConnectionState newState);
    }
}
