## Zset(Sorted Set)
有序集合zset和set一样也是string类型元素的集合，且不允许重复的元素。
不同的是每个类型都会关联一个double类型的分数。redis正是通过分数来为集合中的元素进行从小到大的排序。
zset的元素是唯一的，但分数（score）却可以重复。

适用于排行榜，优先队列。

### 常用命令
##### 添加元素zadd
`zadd key score member score2 member2 ...`：将所有元素以及该元素的分数存放到zset中。如果该元素已经存在则会用新的分数替换原有的分数。返回值是新加入到集合中的元素个数，不包含之前已存在的元素。
例如：
```
127.0.0.1:6379> zadd zset1 5000 xiaoming 666 zhangsan 2000 wangwu
(integer) 3
127.0.0.1:6379> zadd zset1 999 zhangsan
(integer) 0
```
当元素添加进zset集合中后，就已经按照分数从小到大排好序了。

##### 查看指定元素的分数zscore
`zscore key member`：返回指定元素的分数。
例如：
```
127.0.0.1:6379> zscore zset1 xiaoming
"5000"
```

##### 获取集合中元素个数
`zcard key`：返回集合中的元素个数。
例如：
```
127.0.0.1:6379> zcard zset1
(integer) 3
```

##### 删除指定元素
`zrem key member[member...]`：移除集合中指定的元素，可以指定多个元素。
例如：
```
127.0.0.1:6379> zrem zset1 zhangsan wangwu
(integer) 2
```

##### 范围查询（根据下标索引范围）
`zrange key start end [withscores]`：获取集合中下标索引为start-end的元素，[withscores]参数表明返回的元素包含其对应的分数（分数从小到大排列）。
例如：
```
127.0.0.1:6379> zadd zset1 5000 xiaoming 999 zhangsan 2000 wangwu
(integer) 3
127.0.0.1:6379> zrange zset1 0 -1
1) "zhangsan"
2) "wangwu"
3) "xiaoming"
127.0.0.1:6379> zrange zset1 0 -1 withscores
1) "zhangsan"
2) "999"
3) "wangwu"
4) "2000"
5) "xiaoming"
6) "5000"
```
从结果中可看成：zset集合中的元素默认是从小到大已经排好序的。

`zrevrange key start end [withscores]`：获取集合中下标索引为start-end的元素，[withscores]参数表明返回的元素包含其对应的分数（分数从大到小排列）。
例如：
```
127.0.0.1:6379> zadd zset1 5000 xiaoming 999 zhangsan 2000 wangwu
(integer) 3
127.0.0.1:6379> zrevrange zset1 0 -1
1) "xiaoming"
2) "wangwu"
3) "zhangsan"
127.0.0.1:6379> zrevrange zset1 0 -1 withscores
1) "xiaoming"
2) "5000"
3) "wangwu"
4) "2000"
5) "zhangsan"
6) "999"
```
上述结果可认为是一个排行榜。

### 扩展命令
##### 根据排名范围删除元素zremrangebyrank
`zremrangebyrank key start stop`：根据排名范围删除元素。删除排名下标索引为start-stop的元素
例如：
```
127.0.0.1:6379> zadd zset1 5000 xiaoming 999 zhangsan 2000 wangwu
(integer) 3
127.0.0.1:6379> zremrangebyrank zset1 0 1
(integer) 2
```
删除默认（从小到大）排名的下标索引为0-1的元素。

##### 根据分数范围删除元素zremrangebyscore
`zremrangebyscore key min max`：根据分数范围删除元素。删除分数范围为[min,max]的元素。
例如：
```
127.0.0.1:6379> zadd zset1 5000 xiaoming 999 zhangsan 2000 wangwu
(integer) 3
127.0.0.1:6379> zremrangebyscore zset1 999 4000
(integer) 2
```
删除范围包括了min和max。

##### 范围查询（根据分数范围）zrangebyscore
`zrangebyscore key min max [withscores] [limit offset count]`：返回分数在[min,max]范围内的元素并按照分数从小到大排序。
`[withscores]`：显示分数。
`[limit offset count]`：从下标索引为offset的元素开始返回count个元素。
例如：
```
127.0.0.1:6379> zadd zset1 5000 xiaoming 999 zhangsan 2000 wangwu
(integer) 3
127.0.0.1:6379> zrangebyscore zset1 999 9999
1) "zhangsan"
2) "wangwu"
3) "xiaoming"
127.0.0.1:6379> zrangebyscore zset1 999 9999 withscores
1) "zhangsan"
2) "999"
3) "wangwu"
4) "2000"
5) "xiaoming"
6) "5000"
127.0.0.1:6379> zrangebyscore zset1 999 9999 withscores limit 0 2
1) "zhangsan"
2) "999"
3) "wangwu"
4) "2000"
```
可以做类似于mysql等关系型数据库的limit分页查询。

##### 给指定元素增加指定分数zincrby
`zincrby key increment member`：将指定元素的分数增加increment。返回值为增加后的分数。
例如：
```
127.0.0.1:6379> zincrby zset1 2000 zhangsan
"2999"
```

##### 获取指定分数范围内的元素个数zcount
`zcount key min max`：获取分数范围在[min,max]之间的元素个数。
例如：
```
127.0.0.1:6379> zcount zset1 999 9999
(integer) 3
```

##### 获取指定元素的排名
初始集合数据为：
```
127.0.0.1:6379> zrange zset1 0 -1 withscores
1) "zhangsan"
2) "999"
3) "wangwu"
4) "2000"
5) "xiaoming"
6) "5000"
```

`zrank key member`：返回指定元素在集合中的排名。索引从0开始（分数从小到大）。
例如：
```
127.0.0.1:6379> zrank zset1 xiaoming
(integer) 2
127.0.0.1:6379> zrank zset1 wangwu
(integer) 1
127.0.0.1:6379> zrank zset1 zhangsan
(integer) 0
```
zhangsan排第一，wangwu排第二，xiaoming排第三。

`zrevrank key member`：返回指定元素在集合中的排名。索引从0开始（分数从大到小）。
例如：
```
127.0.0.1:6379> zrevrank zset1 xiaoming
(integer) 0
127.0.0.1:6379> zrevrank zset1 wangwu
(integer) 1
127.0.0.1:6379> zrevrank zset1 zhangsan
(integer) 2
```
xiaoming排第一，wangwu排第二，zhangsan排第三。