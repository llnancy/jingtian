package com.sunchaser.microservice.rpc.serialize.compressor.impl;

import com.sunchaser.microservice.rpc.serialize.compressor.Compressor;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.Objects;

/**
 * Snappy实现压缩/解压缩
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class SnappyCompressor implements Compressor {

    @Override
    public byte[] compress(byte[] data) throws IOException {
        Objects.requireNonNull(data);
        return Snappy.compress(data);
    }

    @Override
    public byte[] unCompress(byte[] data) throws IOException {
        Objects.requireNonNull(data);
        return Snappy.uncompress(data);
    }
}
