package org.sunchaser.lombok.test;

import lombok.ToString;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/12
 */
@ToString(exclude = {"id"})
public class ToStringTest {
    private Long id;
    private String address;
    private UserInfo userInfo;

    @ToString
    public static class BaseInfo {
        private String username;
        private String password;
    }

    @ToString(callSuper = true,includeFieldNames = false)
    public static class UserInfo extends BaseInfo {
        private Integer age;
        private Integer gender;
    }
}
