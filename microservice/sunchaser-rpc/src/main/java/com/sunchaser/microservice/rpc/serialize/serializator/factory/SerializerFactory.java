package com.sunchaser.microservice.rpc.serialize.serializator.factory;

import com.google.common.collect.Maps;
import com.sunchaser.microservice.rpc.serialize.serializator.Serializer;
import com.sunchaser.microservice.rpc.serialize.serializator.impl.HessianSerializer;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class SerializerFactory {

    public static Serializer get(byte protocolInfo) {
        return SerializerEnum.match((byte) (protocolInfo & 0x7)).getSerializer();
    }

    @Getter
    public enum SerializerEnum {
        HESSIAN((byte) 0x0, new HessianSerializer()),
        ;
        private final byte protocolInfo;
        private final Serializer serializer;
        private static final Map<Byte, SerializerEnum> enumMap = Maps.newHashMap();

        static {
            for (SerializerEnum serializerEnum : SerializerEnum.values()) {
                enumMap.put(serializerEnum.protocolInfo, serializerEnum);
            }
        }

        public static SerializerEnum match(byte type) {
            return Optional.ofNullable(enumMap.get(type)).orElse(HESSIAN);
        }

        SerializerEnum(byte protocolInfo, Serializer serializer) {
            this.protocolInfo = protocolInfo;
            this.serializer = serializer;
        }
    }
}
