package com.sunchaser.sparrow.java8.chapter11.model;

/**
 * @author sunchaser
 * @since JDK8 2019/9/8
 */
public class UserInfoModel {
    private String uid;

    public UserInfoModel() {
    }

    public UserInfoModel(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "uid='" + uid + '\'' +
                '}';
    }
}
