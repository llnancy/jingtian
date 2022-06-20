package com.sunchaser.microservice.rpc.serialize.compressor;

import java.io.IOException;

/**
 * 数据压缩器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public interface Compressor {

    /**
     * 将数据进行压缩
     *
     * @param data 原比特数组
     * @return 压缩后的数据
     * @throws IOException throw
     */
    byte[] compress(byte[] data) throws IOException;

    /**
     * 将数据解压缩
     *
     * @param data 压缩的数据
     * @return 原数据
     * @throws IOException throw
     */
    byte[] unCompress(byte[] data) throws IOException;
}
