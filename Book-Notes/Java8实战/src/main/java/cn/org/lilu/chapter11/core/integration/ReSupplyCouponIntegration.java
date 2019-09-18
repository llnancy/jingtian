package cn.org.lilu.chapter11.core.integration;

import cn.org.lilu.chapter11.model.UserInfoModel;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/8
 * @Description: 代码防腐层接口
 */
public interface ReSupplyCouponIntegration {

    /**
     * 查询用户信息防腐层接口
     * @param uId 用户UID
     * @return 用户信息model
     */
    UserInfoModel queryUserInfo(String uId);

    /**
     * 补发劵操作防腐层接口
     * @param couponId 优惠券ID
     * @param uId 用户UID
     * @return 补发结果：成功或失败
     */
    Boolean reSupplyCoupon(String couponId, String uId);
}
