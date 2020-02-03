package com.sunchaser.java8.chapter11.core.service.impl;

import com.sunchaser.java8.chapter11.core.integration.ReSupplyCouponIntegration;
import com.sunchaser.java8.chapter11.core.service.ReSupplyCouponService;
import com.sunchaser.java8.chapter11.model.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunchaser
 * @date 2019/9/8
 * @description 基础Service实现类
 */
@Service
public class ReSupplyCouponServiceImpl implements ReSupplyCouponService {

    @Autowired
    private ReSupplyCouponIntegration reSupplyCouponIntegration;

    /**
     * 查询用户信息
     * @param uId 用户UID
     * @return 用户信息model
     */
    @Override
    public UserInfoModel queryUserInfo(String uId) {
        return reSupplyCouponIntegration.queryUserInfo(uId);
    }

    /**
     * 补发劵操作
     * @param couponId 优惠券ID
     * @param uId 用户ID
     * @return 补发结果：成功或失败
     */
    @Override
    public Boolean reSupplyCoupon(String couponId, String uId) {
        return reSupplyCouponIntegration.reSupplyCoupon(couponId,uId);
    }

    /**
     * 补发劵操作
     * @param couponId 优惠券ID
     * @param uId 用户ID
     * @return [UID,"成功或失败"]，返回对应UID。
     */
    @Override
    public Map<String, Object> reSupplyCouponWithUid(String couponId, String uId) {
        Map<String,Object> map = new HashMap<>();
        map.put("uId",uId);
        Boolean result = reSupplyCouponIntegration.reSupplyCoupon(couponId,uId);
        map.put("result",result);
        return map;
    }
}
