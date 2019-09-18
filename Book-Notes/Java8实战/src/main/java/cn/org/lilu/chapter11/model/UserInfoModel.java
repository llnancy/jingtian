package cn.org.lilu.chapter11.model;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/8
 * @Description:
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
