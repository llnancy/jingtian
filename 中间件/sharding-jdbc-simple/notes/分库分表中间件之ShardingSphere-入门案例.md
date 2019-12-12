## ShardingSphere的前尘往事

ShardingSphere的前身是Sharding-JDBC，起源于当当网内部应用框架，于2016年初开源。

2017年，Sharding-JDBC进入2.x阶段，核心功能是数据库治理。

2018年春节前夕，Sharding-JDBC团队于京东数科重新组建，并将其定位为面向云原生的数据库中间件，正式更名为ShardingSphere。Sharding-JDBC进入了3.x时代，功能重心转向了Sharding-proxy及分布式事务上。

美国时间2018年11月10日，分布式数据库中间件生态圈 ShardingSphere正式进入Apache基金会孵化器。

预祝ShardingSphere项目早日成功毕业。

## Apache ShardingSphere简介

Apache ShardingSphere (Incubator) 是一套开源的分布式数据库中间件解决方案组成的生态圈，它由Sharding-JDBC、Sharding-Proxy和Sharding-Sidecar（规划中）这3款相互独立，却又能够混合部署配合使用的产品组成。它们均提供标准化的数据分片、分布式事务和数据库治理功能，可适用于如Java同构、异构语言、云原生等各种多样化的应用场景。

Apache官方发布从4.0.0版本开始。

[Apache ShardingSphere官方文档](https://shardingsphere.apache.org/document/current/cn/overview/)

## Sharding-JDBC简介
定位为轻量级Java框架，在Java的JDBC层提供的额外服务。 它使用客户端直连数据库，以jar包形式提供服务，无需额外部署和依赖，可理解为增强版的JDBC驱动，完全兼容JDBC和各种ORM框架。

## Sharding-JDBC快速入门
我们以一库两表的案例来快速入门。

### 建立数据表
这里我们以简单的订单表为例，作为入门学习使用。

首先建立一库两表的数据库结构。数据库名为sharding_jdbc_db，建立t_order_1和t_order_2两张表。

建表sql语句如下：
```
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
```

### 创建sharding-jdbc-simple项目
使用IDEA创建一个maven的空模板项目，项目名为sharding-jdbc-simple。

在pom.xml文件中需添加的sharding-jdbc-spring-boot-starter依赖如下：
```
<!-- apache sharding-jdbc -->
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>4.0.0-RC1</version>
</dependency>
```

由于我的项目使用了自己定义的统一依赖管理，所以没有指定各个jar包的版本号。

读者可以参考 [sunchaser-boot-dependencies](https://github.com/sunchaser-lilu/plaid-umbrella/tree/master/sunchaser-boot-dependencies) 进行实现，或者单独指定对应版本号。

完整的pom.xml内容如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>中间件</artifactId>
        <groupId>com.sunchaser</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>sharding-jdbc-simple</artifactId>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.sunchaser.boot</groupId>
                <artifactId>sunchaser-boot-dependencies</artifactId>
                <version>1.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- sharding-jdbc依赖 -->
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
        </dependency>
        <!-- test依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- web依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- mybatis依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <!-- druid连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <!-- mysql驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### 编写配置文件
springboot项目可使用properties配置或yaml配置。二选一即可。

#### 基本配置
配置tomcat端口号、应用名和允许重复定义的bean进行覆盖。

properties配置：
```
server.port=520
spring.application.name=sharding-jdbc-simple-demo
# 重复的bean定义进行覆盖
spring.main.allow-bean-definition-overriding=true
```

yaml配置：
```
server:
  port: 520
spring:
  application:
    name: sharding-jdbc-simple-demo
  main:
    # 重复的bean会进行覆盖
    allow-bean-definition-overriding: true
```

#### 配置mybatis

主要配置xml文件映射路径和下划线自动转驼峰。

properties配置：
```
########################################################################
#
#     mybatis配置
#
#########################################################################
# xml映射文件路径
mybatis.mapper-locations=classpath:mapper/**/*.xml
# 下划线自动转驼峰
mybatis.configuration.map-underscore-to-camel-case=true
```

yaml配置：
```
mybatis:
  # xml映射文件路径
  mapper-locations: classpath:mapper/**/*.xml
  configuration:
    # 自动转驼峰
    map-underscore-to-camel-case: true
```

#### 配置数据源

此入门案例是一库两表，只有一个数据源需要配置。

properties配置：

```
########################################################################
#
#     数据源配置
#
#########################################################################
# 数据源名称，多数据源以逗号分隔
spring.shardingsphere.datasource.names=ds1
# 数据源ds1详情配置
spring.shardingsphere.datasource.ds1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.ds1.url=jdbc:mysql://127.0.0.1:3306/sharding_jdbc_db?useUnicode=true&serverTimezone=Asia/Shanghai
spring.shardingsphere.datasource.ds1.username=root
spring.shardingsphere.datasource.ds1.password=ll970722
```

yaml配置：
```
spring:
  # 数据源配置
  shardingsphere:
    datasource:
      # 数据源名称，多数据源以逗号分隔
      names: ds1
      # 数据源ds1详情配置
      ds1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/sharding_jdbc_db?useUnicode=true&serverTimezone=Asia/Shanghai
        username: root
        password: ll970722
```

#### 配置分片规则
使用默认的inline表达式配置分片策略，分片策略包含分片键和分片算法，需符合groovy语法。

properties配置：
```
# 分片规则配置
# 真实数据节点，由数据源名 + 表名组成，以小数点分隔
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=ds1.t_order_$->{1..2}
# 主键列名称，缺省表示不使用自增主键生成器
spring.shardingsphere.sharding.tables.t_order.key-generator.column=order_id
# 自增列值生成器类型，缺省表示使用默认自增列值生成器。
# 可使用自定义的列值生成器或选择内置类型：SNOWFLAKE/UUID/LEAF_SEGMENT
# 这里使用SNOWFLAKE雪花算法
spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE
# 分片策略（分片键和分片算法）
# 分片键
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=order_id
# 分片算法 生成的ID为奇数，插入到t_order_2中，偶数则插入到t_order_1中
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order_$->{order_id % 2 + 1}
```

yaml配置：
```
spring:
  shardingsphere:
    # sharding-jdbc分片规则配置
    sharding:
      tables:
        t_order:
          # 数据节点
          actual-data-nodes: ds1.t_order_$->{1..2}
          # 主键列和主键生成策略：雪花算法
          key-generator:
            column: order_id
            type: SNOWFLAKE
          # 分片策略
          table-strategy:
            inline:
              # 分片键
              sharding-column: order_id
              # 分片算法：生成的ID为奇数，插入到t_order_2中，偶数则插入到t_order_1中
              algorithm-expression: t_order_$->{order_id % 2 + 1}
```

#### 配置日志相关
配置打印执行的真实sql与日志级别。

properties配置：
```
# 打印sharding-jdbc的真实执行sql日志
spring.shardingsphere.props.sql.show=true
# 日志级别配置
logging.level.root=info
logging.level.org.springframework.web=info
logging.level.com.sunchaser.shardingjdbc=debug
logging.level.druid.sql=debug
```

yaml配置：
```
spring:
  shardingsphere:
    props:
      sql:
        # 打印sharding-jdbc的真实执行sql日志
        show: true
        
# 日志级别
logging:
  level:
    root: info
    org:
      springframework:
        web: info
    com:
      sunchaser:
        shardingjdbc: debug
    druid:
      sql: debug
```

至此，配置部分完成，开始编写后端代码。

### 后端测试代码编写

项目包结构如下：

```
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─sunchaser
    │  │          └─shardingjdbc  ------ 存放启动类
    │  │              ├─entity  -------- 存放实体类
    │  │              └─mapper  -------- 存放mapper接口
    │  └─resources
    │      ├─db  ----------------------- 存放建表语句
    │      └─mapper  ------------------- 存放xml映射文件
    └─test
        └─java
            └─com
                └─sunchaser
                    └─shardingjdbc
                        └─mapper  ------ 存放单元测试类
```

入门案例主要是使用Sharding-JDBC进行新增和查询，mybatis的mapper接口和xml映射文件编写方式保持不变。

#### 编写启动类

```
package com.sunchaser.shardingjdbc;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser
 * @date 2019/11/26
 * @description
 * @since 1.0
 */
@SpringBootApplication
public class ShardingJdbcSimpleApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ShardingJdbcSimpleApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
```

#### 编写订单实体类

使用lombok工具包的@Data和@Builder注解生成getter、setter和建造者模式代码。

```
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
```

#### 编写mapper接口

1. 插入订单（注解实现）
2. 根据订单ID的集合查询订单信息集合（注解实现）
3. 根据订单ID的集合查询订单信息集合（XML实现）

```
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
     * xml 实现
     * @param orderIds
     * @return
     */
    List<OrderEntity> selectByXml(@Param("orderIds") List<Long> orderIds);
}
```

OrderMapper的selectByXml方法对应的XML配置如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sunchaser.shardingjdbc.mapper.OrderMapper">
    <select id="selectByXml"
            resultType="com.sunchaser.shardingjdbc.entity.OrderEntity">
        select * from t_order where order_id in
        <foreach collection='orderIds' item='orderId' open='(' separator=',' close=')'>
            #{orderId}
        </foreach>
    </select>
</mapper>
```

#### 编写单元测试代码

首先循环调用OrderMapper的insert方法插入20条订单数据，然后分别选择同一个订单表的两个订单orderId和选择不同订单表的两个orderId进行`in`查询。

```
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
            OrderEntity orderEntity = OrderEntity.builder()
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
```

由于前面配置了sql日志打印，我们可在控制台看到执行的逻辑SQL和真实SQL。

以查询方法selectByAnnotation为例，执行日志如下：

```
2019-12-12 11:05:36.399 DEBUG 17328 --- [           main] c.s.s.m.OrderMapper.selectByAnnotation   : ==>  Preparing: select * from t_order t where t.order_id in ( ? , ? ) 
2019-12-12 11:05:36.459 DEBUG 17328 --- [           main] c.s.s.m.OrderMapper.selectByAnnotation   : ==> Parameters: 406907714078244865(Long), 407114454203891712(Long)
2019-12-12 11:05:37.778  INFO 17328 --- [           main] ShardingSphere-SQL                       : Rule Type: sharding
2019-12-12 11:05:37.783  INFO 17328 --- [           main] ShardingSphere-SQL                       : Logic SQL: select * from t_order t where t.order_id in   (    ?   ,   ?   )
2019-12-12 11:05:37.784  INFO 17328 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatement(super=DQLStatement(super=AbstractSQLStatement(type=DQL, tables=Tables(tables=[Table(name=t_order, alias=Optional.of(t))]), routeConditions=Conditions(orCondition=OrCondition(andConditions=[AndCondition(conditions=[Condition(column=Column(name=order_id, tableName=t_order), operator=IN, compareOperator=null, positionValueMap={}, positionIndexMap={0=0, 1=1})])])), encryptConditions=Conditions(orCondition=OrCondition(andConditions=[])), sqlTokens=[TableToken(tableName=t_order, quoteCharacter=NONE, schemaNameLength=0)], parametersIndex=2, logicSQL=select * from t_order t where t.order_id in   (    ?   ,   ?   ))), containStar=true, firstSelectItemStartIndex=7, selectListStopIndex=7, groupByLastIndex=0, items=[StarSelectItem(owner=Optional.absent())], groupByItems=[], orderByItems=[], limit=null, subqueryStatement=null, subqueryStatements=[], subqueryConditions=[])
2019-12-12 11:05:37.786  INFO 17328 --- [           main] ShardingSphere-SQL                       : Actual SQL: ds1 ::: select * from t_order_1 t where t.order_id in   (    ?   ,   ?   ) ::: [406907714078244865, 407114454203891712]
2019-12-12 11:05:37.786  INFO 17328 --- [           main] ShardingSphere-SQL                       : Actual SQL: ds1 ::: select * from t_order_2 t where t.order_id in   (    ?   ,   ?   ) ::: [406907714078244865, 407114454203891712]
2019-12-12 11:05:38.062 DEBUG 17328 --- [           main] c.s.s.m.OrderMapper.selectByAnnotation   : <==      Total: 2
[OrderEntity(orderId=407114454203891712, price=1.11, userId=2019xxxxxxxx, status=SUCCESS), OrderEntity(orderId=406907714078244865, price=1.11, userId=2019xxxxxxxxxxxxxxx, status=SUCCESS)]
```

可看到执行的Logic SQL为：
```
select * from t_order t where t.order_id in ( ? , ? )
```

Sharding-JDBC的路由引擎根据解析上下文匹配数据库和表的分片策略，路由到具体的库和表。再经过改写引擎，将逻辑SQL改写为真正执行的SQL。

从日志中可看到改写后的Actual SQL为：

```
select * from t_order_1 t where t.order_id in   (    ?   ,   ?   ) ::: [406907714078244865, 407114454203891712]
select * from t_order_2 t where t.order_id in   (    ?   ,   ?   ) ::: [406907714078244865, 407114454203891712]
```

结果归并，真实的SQL执行后会返回多个结果集，归并引擎从各个数据节点拿到结果集后组合成一个结果集并返回给客户端。

至此，入门案例就演示结束。下篇文章将对Sharding-JDBC中的一些核心概念进行具体的解释。

### 小结
这篇文章主要是对Apache ShardingSphere 4.x与Spring Boot 2.1.x进行整合，使用了内置的SNOWFLAKE雪花算法生成分布式全局唯一ID和使用默认的inline表达式配置分片策略，完成了最简单的一库两表的分库分表，并对新增和查询操作进行了测试。