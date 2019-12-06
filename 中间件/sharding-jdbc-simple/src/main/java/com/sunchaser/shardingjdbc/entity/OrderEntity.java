package com.sunchaser.shardingjdbc.entity;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * @author sunchaser
 * @date 2019/11/29
 * @description
 * @since 1.0
 */
public class OrderEntity {
    private Long orderId;
    private BigDecimal price;
    private String userId;
    private String status;

    public OrderEntity() {
    }

    public OrderEntity(Builder builder) {
        setOrderId(builder.orderId);
        setPrice(builder.price);
        setUserId(builder.userId);
        setStatus(builder.status);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderEntity setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public OrderEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("price", price)
                .add("userId", userId)
                .add("status", status)
                .toString();
    }

    public static final class Builder {
        private Long orderId;
        private BigDecimal price;
        private String userId;
        private String status;

        private Builder() {
        }

        public Builder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }
}
