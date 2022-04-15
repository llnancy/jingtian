package com.sunchaser.rpc.serialize.serializator.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.sunchaser.rpc.serialize.serializator.Serializer;

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
        ByteArrayOutputStream op = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(op);
        hessianOutput.writeObject(obj);
        return op.toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(data);
        HessianInput hessianInput = new HessianInput(is);
        hessianInput.readObject(clazz);
        return (T) hessianInput.readObject(clazz);
    }
}
