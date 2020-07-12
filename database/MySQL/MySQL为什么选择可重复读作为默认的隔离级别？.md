## 问题

在`Oracle`和`SqlServer`关系型数据库中，默认的事务隔离级别是读已提交。为什么`MySQL`的默认隔离级别选择可重复读呢？

## 解答

这个问题得从主从复制说起，`MySQL`的主从复制是基于`binlog`复制的。

`binlog`有以下三种格式：

- `Statement`：记录修改的`SQL`语句。
- `Row`：记录每一行数据的变更。
- `Mixed`：`Statement`和`Row`模式的混合。

`MySQL`在`5.0`版本之前，`binlog`只支持`Statement`格式，在读已提交隔离级别下，这种格式的主从复制是存在问题的。

我们来看下面的例子：

创建一张`S`表，并初始化一条数据。

```SQL
CREATE TABLE `S` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `val` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `S` (`val`) values ('hello');
```

假设此时我们的`MySQL`是`5.0`版本，`binlog`为`Statement`格式，且隔离级别为读已提交。在主从架构下，主库上执行以下事务：

| Statement1 | Statement2 |
| ---------- | ---------- |
| begin      | begin      |
| delete from `S` where id < 5; ||
|| insert into `S` (`val`) values ('java'); |
|| commit; |
| commit; ||

此时在主库上执行`select * from S`，会输出一条记录`java`；而在从库执行该语句会输出`Empty Set`。

主从库数据出现了不一致。那原因是什么呢？

在主库上，`Statement1`的删除先执行，在其提交事务之前`Statement2`插入数据并提交，`Statement2`插入的数据不会被删除，最后`Statement1`事务提交，也就是先删除后插入，表中留下了`Statement2`事务中插入的数据。在这个过程中，由于`Statement2`先提交，`binlog`先将插入记录下来，然后记录后提交的删除。同步到从库上时，就是先插入后删除。所以导致了主从数据不一致。

那么如何解决该问题呢？

方案一：使用可重复读隔离级别，该级别下引入间隙锁，在`Statement1`执行删除时，间隙锁会锁住一个`id`区间（这个区间范围有一定的规则），在这个区间内的操作都会阻塞，所以`Statement2`执行插入会被阻塞住，直到`Statement1`事务提交才执行。

方案二：将`binlog`的格式修改为`Row`格式，基于行的复制，这样就保证了删除和插入的执行顺序。但该特性在`5.0`版本之后才引入，所以，`MySQL`为了保证主从同步数据一致性，将默认隔离级别设置为可重复读。

间隙锁的存在，导致可重复读隔离级别出现死锁的几率变大，一旦出现死锁，对业务的影响将是不可预料的，所以，实际业务开发中，并不推荐使用默认的可重复读隔离级别，而是推荐使用读已提交隔离级别。

## 总结

关于间隙锁，我将单独写一篇博客进行详细介绍。这里只需理解到会锁住一个区间即可，在这个区间内的操作都会阻塞。

`MySQL`的早期版本没有间隙锁，在读已提交隔离级别下主从同步会出现数据不一致的情况，所以将默认的隔离级别设为了读已提交。

## 参考

- [https://www.cnblogs.com/rjzheng/p/10510174.html](https://www.cnblogs.com/rjzheng/p/10510174.html)