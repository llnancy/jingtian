package com.sunchaser.microservice.rpc.serialize.serializator.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sunchaser.microservice.rpc.serialize.serializator.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian序列化器
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/4/12
 */
public class HessianSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(bos);
        output.writeObject(obj);
        output.flush();
        return bos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Hessian2Input input = new Hessian2Input(bis);
        return (T) input.readObject(clazz);
    }

}
