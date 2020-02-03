package com.sunchaser.java8.chapter11.model;

/**
 * @author sunchaser
 * @date 2019/9/8
 * @description
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
