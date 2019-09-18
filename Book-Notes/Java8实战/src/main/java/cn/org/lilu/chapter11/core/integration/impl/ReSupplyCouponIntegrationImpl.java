package cn.org.lilu.chapter11.core.integration.impl;

import cn.org.lilu.chapter11.core.integration.ReSupplyCouponIntegration;
import cn.org.lilu.chapter11.model.UserInfoModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/8
 * @Description: 模拟代码防腐层实现类，模拟调用外部服务进行用户信息查询和补发劵操作
 */
@Component
public class ReSupplyCouponIntegrationImpl implements ReSupplyCouponIntegration {

    private static List<UserInfoModel> users = new ArrayList<>();

    /**
     * 初始化操作，模拟远程用户数据
     */
    static {
        for (int i = 0; i < 250; i++) {
            users.add(new UserInfoModel(String.valueOf(i)));
        }
    }

    /**
     * 模拟查找用户操作，不存在则UID则新增一个。
     * @param uId 用户UID
     * @return 用户信息model
     */
    @Override
    public UserInfoModel queryUserInfo(String uId) {
        try {
            // 模拟调用远程服务耗时
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users.get(Integer.valueOf(uId));
    }

    /**
     * 模拟补发劵操作
     * @param couponId 优惠券ID
     * @param uId 用户id
     * @return 补发劵结果：成功或失败
     */
    @Override
    public Boolean reSupplyCoupon(String couponId, String uId) {
        try {
            // 模拟调用远程服务耗时
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 模拟成功或失败概率
        return new Random().nextInt(100) < 90;
    }
}
