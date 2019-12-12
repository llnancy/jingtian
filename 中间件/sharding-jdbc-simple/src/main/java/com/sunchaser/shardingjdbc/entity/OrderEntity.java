package com.sunchaser.shardingjdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sunchaser
 * @date 2019/11/29
 * @description
 * @since 1.0
 */
@Data
@Builder
public class OrderEntity {

    /**
     * 订单id：雪花算法生成的分布式唯一ID
     */
    private Long orderId;

    /**
     * 订单价格
     */
    private BigDecimal price;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单状态
     */
    private String status;
}
