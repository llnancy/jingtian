DROP TABLE IF EXISTS t_order;
-- 主表结构
CREATE TABLE `sharding_jdbc_db`.`t_order`  (
  `order_id` varchar(32) NOT NULL COMMENT '主键：订单id',
  `price` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `status` varchar(16) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`order_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- 分表结构
DROP TABLE IF EXISTS t_order_1;
DROP TABLE IF EXISTS t_order_2;
CREATE TABLE `sharding_jdbc_db`.`t_order_1`  (
  `order_id` varchar(32) NOT NULL COMMENT '主键：订单id',
  `price` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `status` varchar(16) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`order_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

CREATE TABLE `sharding_jdbc_db`.`t_order_2`  (
  `order_id` varchar(32) NOT NULL COMMENT '主键：订单id',
  `price` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `status` varchar(16) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`order_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;