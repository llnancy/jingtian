package com.sunchaser.microservice.rpc.common;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class RpcContext {

    public static final int HEADER_SIZE = 16;

    public static final short MAGIC = (short) 0x208;

    public static final int HEARTBEAT_CODE = 1;

    public static boolean isHeartBeat(byte protocolInfo) {
        return (protocolInfo & 32) != 0;
    }

    public static boolean isRpc(byte protocolInfo) {
        return (protocolInfo & 1) != 1;
    }
}
