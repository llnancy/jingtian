package com.sunchaser.shardingjdbc.mapper;

import com.sunchaser.shardingjdbc.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunchaser
 * @date 2019/11/28
 * @description
 * @since 1.0
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入
     * @param orderEntity 订单实体
     * @return 影响记录行数
     */
    @Insert("insert into t_order(price,user_id,status) values(#{price},#{userId},#{status})")
    Integer insert(OrderEntity orderEntity);

    /**
     * 注解实现
     * 根据订单id集合查询订单集合
     * @param orderIds 订单ID集合
     * @return 订单集合
     */
    @Select(" <script> " +
            " select * from t_order t where t.order_id in " +
            " <foreach collection='orderIds' open='(' separator=',' close=')' item='orderId'> " +
            " #{orderId} " +
            " </foreach> " +
            " </script> ")
    List<OrderEntity> selectByAnnotation(@Param("orderIds") List<Long> orderIds);

    /**
     * xml
     * @param orderIds
     * @return
     */
    List<OrderEntity> selectByXml(@Param("orderIds") List<Long> orderIds);
}
