package com.sunchaser.microservice.rpc.serialize.compressor.factory;

import com.google.common.collect.Maps;
import com.sunchaser.microservice.rpc.serialize.compressor.Compressor;
import com.sunchaser.microservice.rpc.serialize.compressor.impl.SnappyCompressor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class CompressorFactory {

    public static Compressor get(byte protocolInfo) {
        return CompressorEnum.match((byte) (protocolInfo & 24)).getCompressor();
    }

    @Getter
    public enum CompressorEnum {
        SNAPPY((byte) 0x0, new SnappyCompressor()),
        ;
        private final byte protocolInfo;
        private final Compressor compressor;
        private static final Map<Byte, CompressorEnum> enumMap = Maps.newHashMap();

        static {
            for (CompressorEnum compressorEnum : CompressorEnum.values()) {
                enumMap.put(compressorEnum.protocolInfo, compressorEnum);
            }
        }

        public static CompressorEnum match(byte protocolInfo) {
            return Optional.ofNullable(enumMap.get(protocolInfo)).orElse(SNAPPY);
        }

        CompressorEnum(byte protocolInfo, Compressor compressor) {
            this.protocolInfo = protocolInfo;
            this.compressor = compressor;
        }
    }
}
