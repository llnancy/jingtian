package com.sunchaser.rpc.serialize.compressor.factory;

import com.google.common.collect.Maps;
import com.sunchaser.rpc.serialize.compressor.Compressor;
import com.sunchaser.rpc.serialize.compressor.impl.SnappyCompressor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class CompressorFactory {
    public static Compressor get(byte extraInfo) {
        return CompressorEnum.match((byte) (extraInfo & 24)).getCompressor();
    }

    @Getter
    public enum CompressorEnum {
        SNAPPY((byte) 0x0, new SnappyCompressor()),
        ;
        private final byte extraInfo;
        private final Compressor compressor;
        private static final Map<Byte, CompressorEnum> enumMap = Maps.newHashMap();

        static {
            for (CompressorEnum compressorEnum : CompressorEnum.values()) {
                enumMap.put(compressorEnum.extraInfo, compressorEnum);
            }
        }

        public static CompressorEnum match(byte extraInfo) {
            return Optional.ofNullable(enumMap.get(extraInfo)).orElse(SNAPPY);
        }

        CompressorEnum(byte extraInfo, Compressor compressor) {
            this.extraInfo = extraInfo;
            this.compressor = compressor;
        }
    }
}
