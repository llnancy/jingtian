package com.sunchaser.javase.base.serizlizable;

import java.io.Serializable;

/**
 * 序列化类
 * @author sunchaser
 * @date 2020/3/19
 * @since 1.0
 */
public class SerializableClass implements Serializable {
    private static final long serialVersionUID = 5135631042912401553L;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public SerializableClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public SerializableClass setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SerializableClass{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
