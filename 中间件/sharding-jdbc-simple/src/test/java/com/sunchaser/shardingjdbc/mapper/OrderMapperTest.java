package com.sunchaser.shardingjdbc.mapper;

import com.google.common.collect.Lists;
import com.sunchaser.shardingjdbc.entity.OrderEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunchaser
 * @date 2019/11/28
 * @description
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void insert() {
        for (int i = 0; i < 20; i++) {
            OrderEntity orderEntity = OrderEntity.newBuilder()
                    .price(new BigDecimal("1.11"))
                    .userId("2019xxxxxxxx")
                    .status("SUCCESS")
                    .build();
            Integer success = orderMapper.insert(orderEntity);
            System.out.println(success);
        }
    }

    @Test
    public void selectByAnnotation() {
        List<Long> orderIds = Lists.newArrayList(406907714078244865L,407114454203891712L);
        List<OrderEntity> orderEntities = orderMapper.selectByAnnotation(orderIds);
        System.out.println(orderEntities);
    }

    @Test
    public void selectByXml() {
        List<Long> orderIds = Lists.newArrayList(406907714078244865L,407114454203891712L);
        List<OrderEntity> orderEntities = orderMapper.selectByXml(orderIds);
        System.out.println(orderEntities);
    }
}