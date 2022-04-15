## 扩展知识
#### 消息订阅与发布
适用于新闻系统。

`subscribe channel`：订阅channel频道。
例如：`subscribe mychat`。订阅mychat这个频道。
```
127.0.0.1:6379> subscribe mychat
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "mychat"
3) (integer) 1
```
`publish channel content`：在指定的频道channel中发布消息content。
打开一个新的客户端，发布消息：
```
127.0.0.1:6379> publish mychat 'hello'
(integer) 1
```
返回值表示有几个客户端收到了消息。
订阅mychat频道的客户端中显示：
```
127.0.0.1:6379> subscribe mychat
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "mychat"
3) (integer) 1
1) "message"
2) "mychat"
3) "hello"
```
收到了mychat频道中发布的一条消息，内容为hello。

`psubscribe channel*`：批量订阅频道。
例如：`psubscribe mychat*`。订阅以mychat开头的频道。
打开一个新的客户端：
```
127.0.0.1:6379> psubscribe mychat*
Reading messages... (press Ctrl-C to quit)
1) "psubscribe"
2) "mychat*"
3) (integer) 1
```
在发布消息的客户端中发布消息：
```
127.0.0.1:6379> publish mychat 'hello'
(integer) 2
```
订阅了mychat的客户端（`subscribe mychat`）中显示：
```
127.0.0.1:6379> subscribe mychat
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "mychat"
3) (integer) 1
1) "message"
2) "mychat"
3) "hello"
1) "message"
2) "mychat"
3) "hello"
```
批量订阅了mychat\*的客户端（`psubscribe mychat*`）中显示：
```
127.0.0.1:6379> psubscribe mychat*
Reading messages... (press Ctrl-C to quit)
1) "psubscribe"
2) "mychat*"
3) (integer) 1
1) "pmessage"
2) "mychat*"
3) "mychat"
4) "hello"
```

#### 多数据库
在mysql等关系型数据库中可以自己用语句自定义创建数据库：`create database xxx`
redis也是有数据库的，但redis已经将其提前创建好了。
默认有16个数据库。数据库名依次为：0,1,2...15。
默认情况下，在redis上做的所有数据操作都是在0号数据库上进行的。数据库和数据库之间不能共享键值对。

切换数据库：`select 数据库名`

```
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> 
```

将键值对数据在数据库之间移植：`move key 数据库名`

例如：将当前库的user移植到1号数据库。
命令：`move user 1`
```
127.0.0.1:6379> keys *
 1) "zset1"
 2) "sunion"
 3) "list1"
 4) "user"
 5) "list3"
 6) "set2"
 7) "sinter"
 8) "sdiff"
 9) "list2"
10) "visit_count"
127.0.0.1:6379> move user 1
(integer) 1
127.0.0.1:6379> keys *
1) "zset1"
2) "sunion"
3) "list1"
4) "list3"
5) "set2"
6) "sinter"
7) "sdiff"
8) "list2"
9) "visit_count"
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> keys *
1) "user"
```

慎用：
当前数据库的清空：`flushdb`
redis服务器数据的清空：`flushall`

#### redis批量化操作-事务
mysql等关系型数据库中的事务：保证数据的完整性和一致性。安全。
而redis的事务是为了进行redis命令的批量化执行。
事务操作：
`multi`：开启事务。用于事务的开启，其后执行的redis命令都将被存入命令队列，直到执行exec命令时，队列中的命令才会被原子性的执行。类似于关系型数据库中的begin transaction。
`exec`：提交事务。命令队列中的命令被批量化的原子执行。类似于关系型数据库中的commit。
`discard`：事务回滚。命令队列中的命令全部不会被执行。类似于关系型数据库中的rollback。
需要注意的是：redis中提交事务后，如果命令队列中有1条或多条redis命令执行失败后，其后的redis命令仍然会正常执行，不会进行回滚操作（这和关系型数据库不同）。说的再明白一点：redis中的事务只是为了批量化的执行redis命令，而不是保证数据的安全。
例：
```
127.0.0.1:6379[1]> multi
OK
127.0.0.1:6379[1]> set username aaa
QUEUED
127.0.0.1:6379[1]> incr username
QUEUED
127.0.0.1:6379[1]> get username
QUEUED
127.0.0.1:6379[1]> incr username
QUEUED
127.0.0.1:6379[1]> set username bbb
QUEUED
127.0.0.1:6379[1]> get username
QUEUED
127.0.0.1:6379[1]> exec
1) OK
2) (error) ERR value is not an integer or out of range
3) "aaa"
4) (error) ERR value is not an integer or out of range
5) OK
6) "bbb"
```
尽管事务中的某些命令执行失败报错，后面的命令也会被正常的执行。

#### redis持久化
持久化：将数据保存在硬盘上。
内存：高效，断电之后数据消失。
硬盘：读写速度低于内存，断电之后数据依然存在。

关系型数据库mysql持久化：任何增删改语句，都是在硬盘上做的操作，断电之后，数据仍然存在于硬盘上。
非关系型数据库redis持久化：默认情况下，所有的增删改语句，都是在内存中进行，断电之后，内存中的数据不存在。

断电之后，redis的部分数据会丢失，丢失的数据是保存在内存中的数据。

redis的持久化策略：
`RDB（redis database）`：默认持久化机制。相当于照快照，保存的是一种状态。很大的数据可能只需要几KB的快照就能保存。

优点：①快照保存数据速度极快，还原数据速度极快。②适用于灾难备份。

缺点：①RDB机制符合要求就会照快照。随时随地启动，会占用一部分系统资源。很可能造成内存不足直接宕机。（宕机后，服务器关闭，属于非正常关闭，不会进行RDB持久化，内存中的数据会丢失）

RDB照快照的时机：
1）服务器正常关闭时。（./bin/redis-cli shutdown）
2）key满足一定条件（配置文件中的配置）。
```
# key发生变化：key数据添加、修改和删除。
# 每900（15分钟）秒至少有1个key发生变化，则dump内存快照。
save 900 1
# 每300（5分钟）秒至少有10个key发生变化，则dump内存快照。
save 300 10
# 每60（1分钟）秒至少有10000个key发生变化，则dump内存快照。
save 60 10000
```
适用于内存比较充裕的服务器。

`AOF（append only file）`：使用日志功能保存数据操作。默认AOF是关闭的。AOF保存的是导致key发生变化的命令。
开启AOF机制：
将配置文件中的`appendonly no`改为`appendonly yes`。
AOF策略配置：
`always`：每次有key发生变化时都会写入AOF文件。
`everysec`：每秒钟同步一次。该策略为AOF的缺省策略。
`no`：从不同步。高效但数据不会被持久化。

优点：持续性占用极少量的内存资源。
缺点：当key进行频繁的修改操作时，aof日志文件会特别大，不适用于灾难备份。数据恢复效率远远低于RDB机制。

适用于内存较小的服务器。