package com.sunchaser.rpc.common;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class RpcContext {
    public static final int HEADER_SIZE = 16;

    public static final short MAGIC = (short) 0x208;

    public static boolean isHeartBeat(byte extraInfo) {
        return (extraInfo & 32) != 0;
    }
}
