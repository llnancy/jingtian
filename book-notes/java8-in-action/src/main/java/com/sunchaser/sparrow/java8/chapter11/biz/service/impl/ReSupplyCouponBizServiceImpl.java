package com.sunchaser.sparrow.java8.chapter11.biz.service.impl;

import com.sunchaser.sparrow.java8.chapter11.biz.service.ReSupplyCouponBizService;
import com.sunchaser.sparrow.java8.chapter11.core.service.ReSupplyCouponService;
import com.sunchaser.sparrow.java8.chapter11.model.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 业务Service接口实现类
 * @author sunchaser
 * @since JDK8 2019/9/8
 */
@Service
public class ReSupplyCouponBizServiceImpl implements ReSupplyCouponBizService {

    @Autowired
    private ReSupplyCouponService reSupplyCouponService;

    /**
     * 同步 劵补发操作
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 失败的用户UID集合
     */
    @Override
    public List<String> syncReSupplyCoupon(List<String> uIds, String couponId) {
        List<String> result = new ArrayList<>();
        List<UserInfoModel> userInfoModelList = new ArrayList<>();
        // 循环验证UID有效性
        for (String uId : uIds) {
            // 查询UID对应用户信息
            UserInfoModel userInfoModel = reSupplyCouponService.queryUserInfo(uId);
            if (userInfoModel != null) {
                // UID存在，放入待进行补发用户集合
                userInfoModelList.add(userInfoModel);
            } else {
                // UID不存在，放入返回结果集合
                result.add(uId);
            }
        }
        // 循环进行劵补发
        for (UserInfoModel userInfoModel : userInfoModelList) {
            Boolean flag = false;
            try {
                flag = reSupplyCouponService.reSupplyCoupon(couponId,userInfoModel.getUid());
            } catch (Exception e) {
                // 异常处理
            }
            if (!flag) {
                // 补发劵失败，放入返回结果集合
                result.add(userInfoModel.getUid());
            }
        }
        return result;
    }

    /**
     * 初始化线程池
     */
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 声明Future
     */
    private static Future<List<String>> future;

    /**
     * 使用Callable封装耗时操作
     */
    class AsyncReSupplyCouponCallable implements Callable<List<String>> {
        // 通过构造函数间接传递参数给call方法
        private List<String> uIds;
        private String couponId;
        public AsyncReSupplyCouponCallable(List<String> uIds, String couponId) {
            this.uIds = uIds;
            this.couponId = couponId;
        }

        @Override
        public List<String> call() throws Exception {
            // 调用同步的补发劵方法
            return syncReSupplyCoupon(uIds,couponId);
        }
    }

    /**
     * 异步 劵补发操作 基于JDK 5的Future接口
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     */
    @Override
    public void asyncFutureReSupplyCoupon(List<String> uIds, String couponId) {
        future = executorService.submit(new AsyncReSupplyCouponCallable(uIds,couponId));
        executorService.shutdown();
    }


    /**
     * 获取补发劵失败的UIDs在前端显示
     * 由前端控制调用该方法的时机
     * 根据上传的UIDs数量做轮询，时间可以设置久一点。
     * @return 补发失败的UID集合
     */
    @Override
    public List<String> getFailedUIDs() {
        List<String> result = new ArrayList<>();
        try {
            if (future != null) {
                // 如果调用get方法时，Callable中的任务还未执行完，则线程阻塞在这里。
                // 使用重载的get方法设置超时时间为50秒。如果发生阻塞，则最多等待50秒后退出。
                result = future.get(50, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            // 线程等待过程中被中断
        } catch (ExecutionException e) {
            // 计算抛出一个异常
        } catch (TimeoutException e) {
            // 在Future对象完成之前超时已过期
        }
        return result;
    }

    /**
     * 使用并行流 补发劵
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 补发失败的用户UID集合
     */
    @Override
    public List<String> parallelReSupplyCoupon(List<String> uIds, String couponId) {
        List<String> failUidList = new ArrayList<>();
        // 使用并行流验证UID是否合法，按是否合法进行分区：不存在的为true区
        Map<Boolean, List<UserInfoModel>> userInfoModelMap = uIds.parallelStream()
                .map(uId -> reSupplyCouponService.queryUserInfo(uId))
                .collect(Collectors.partitioningBy(Objects::isNull));
        // 取出不合法的UID加入补发失败的集合中
        userInfoModelMap.get(true)
                .parallelStream()
                .map(userInfoModel -> failUidList.add(userInfoModel.getUid()))
                .collect(Collectors.toList()); // 触发中间操作
        // 取出合法的UID进行补发劵操作
        List<Map<String, Object>> reSupplyCouponResult = userInfoModelMap.get(false)
                .parallelStream()
                .map(userInfoModel -> reSupplyCouponService.reSupplyCouponWithUid(couponId, userInfoModel.getUid()))
                .collect(Collectors.toList());
        // 从补发劵结果中取出补发失败的加入补发失败的集合中
        reSupplyCouponResult.parallelStream()
                .filter(map -> !(Boolean) map.get("result"))
                .map(map -> failUidList.add(String.valueOf(map.get("uId"))))
                .collect(Collectors.toList());
        return failUidList;
    }

    /**
     * 异步 劵补发操作 每一个UID之间都是异步的 基于JDK 8的CompletableFuture接口
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 补发失败的用户UID集合
     */
    @Override
    public List<String> asyncCompletableFutureReSupplyCoupon(List<String> uIds, String couponId) {
        List<String> failUidList = new ArrayList<>();
        // 使用CompletableFuture异步操作：验证UID是否存在系统中
        List<CompletableFuture<UserInfoModel>> list = uIds.stream()
                .map(uId -> CompletableFuture.supplyAsync(
                        () -> reSupplyCouponService.queryUserInfo(uId))
                ).collect(Collectors.toList());
        // 等待所有异步操作执行结束，分区筛选出存在的UIDs和不存在的UIDs
        Map<Boolean, List<UserInfoModel>> joinMap = list.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.partitioningBy(Objects::isNull));
        // 将不存在的UIDs加入补发失败的集合中
        joinMap.get(true)
                .stream()
                .map(userInfoModel -> failUidList.add(userInfoModel.getUid()))
                .collect(Collectors.toList());
        // 给存在的UIDs补发劵
        List<CompletableFuture<Map<String, Object>>> reSupplyCouponResult = joinMap.get(false)
                .stream()
                .map(userInfoModel -> CompletableFuture.supplyAsync(
                        () -> reSupplyCouponService.reSupplyCouponWithUid(couponId, userInfoModel.getUid()))
                ).collect(Collectors.toList());
        // 等待所有异步操作执行结束，筛选出补发劵失败的UIDs存入返回结果集合中
        reSupplyCouponResult.stream()
                .map(CompletableFuture::join)
                .filter(r -> !(Boolean) r.get("result"))
                .map(r -> failUidList.add(String.valueOf(r.get("uId"))))
                .collect(Collectors.toList());
        return failUidList;
    }


    /**
     * 定制执行器-线程池大小为UIDs的数量：设置为守护线程，当程序退出时，线程也会被回收。
     */
    private final Executor executor = Executors.newFixedThreadPool(125, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    /**
     * 异步 劵补发操作 定制CompletableFuture接口的执行器
     * @param uIds 用户UID集合
     * @param couponId 优惠券ID
     * @return 补发失败的用户UID集合
     */
    @Override
    public List<String> asyncCompletableFutureCustomExecutorReSupplyCoupon(List<String> uIds, String couponId) {
        List<String> failUidList = new ArrayList<>();
        // 使用CompletableFuture异步操作：验证UID是否存在系统中
        List<CompletableFuture<UserInfoModel>> list = uIds.stream()
                .map(uId -> CompletableFuture.supplyAsync(
                        () -> reSupplyCouponService.queryUserInfo(uId),executor)
                ).collect(Collectors.toList());
        // 等待所有异步操作执行结束，分区筛选出存在的UIDs和不存在的UIDs
        Map<Boolean, List<UserInfoModel>> joinMap = list.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.partitioningBy(Objects::isNull));
        // 将不存在的UIDs加入补发失败的集合中
        joinMap.get(true)
                .stream()
                .map(userInfoModel -> failUidList.add(userInfoModel.getUid()))
                .collect(Collectors.toList());
        // 给存在的UIDs补发劵
        List<CompletableFuture<Map<String, Object>>> reSupplyCouponResult = joinMap.get(false)
                .stream()
                .map(userInfoModel -> CompletableFuture.supplyAsync(
                        () -> reSupplyCouponService.reSupplyCouponWithUid(couponId, userInfoModel.getUid()),executor)
                ).collect(Collectors.toList());
        // 等待所有异步操作执行结束，筛选出补发劵失败的UIDs存入返回结果集合中
        reSupplyCouponResult.stream()
                .map(CompletableFuture::join)
                .filter(r -> !(Boolean) r.get("result"))
                .map(r -> failUidList.add(String.valueOf(r.get("uId"))))
                .collect(Collectors.toList());
        return failUidList;
    }
}
