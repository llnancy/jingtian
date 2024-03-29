package io.github.llnancy.jingtian.javase.base.serizlizable;

import java.io.Serializable;

/**
 * 序列化类
 *
 * @author sunchaser
 * @since JDK8 2020/3/19
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
        return "SerializableClass{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
