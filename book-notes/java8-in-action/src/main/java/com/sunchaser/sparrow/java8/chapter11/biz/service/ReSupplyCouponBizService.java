package com.sunchaser.sparrow.java8.chapter11.biz.service;

import java.util.List;

/**
 * @author sunchaser
 * @date 2019/9/8
 * @description 业务service接口
 */
public interface ReSupplyCouponBizService {

    /**
     * 同步 劵补发操作
     *
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 失败的用户UID集合
     */
    List<String> syncReSupplyCoupon(List<String> uIds, String couponId);

    /**
     * 异步 劵补发操作 基于JDK 5的Future接口
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     */
    void asyncFutureReSupplyCoupon(List<String> uIds,String couponId) ;

    /**
     * 获取失败的UID
     * @return 补发失败的UID集合
     */
    List<String> getFailedUIDs();

    /**
     * 并行补发劵
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 补发失败的用户UID集合
     */
    List<String> parallelReSupplyCoupon(List<String> uIds,String couponId);

    /**
     * 异步 劵补发操作 每一个UID之间都是异步的 基于JDK 8的CompletableFuture接口
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 补发失败的用户UID集合
     */
    List<String> asyncCompletableFutureReSupplyCoupon(List<String> uIds,String couponId);

    /**
     * 异步 劵补发操作 定制CompletableFuture接口的执行器
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 补发失败的用户UID集合
     */
    List<String> asyncCompletableFutureCustomExecutorReSupplyCoupon(List<String> uIds,String couponId);
}
