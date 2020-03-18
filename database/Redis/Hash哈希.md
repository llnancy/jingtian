## Hash
redis中的Hash类型可以看成具有String key和String value的map容器。所以该类型非常适合于存储值对象的信息（对象属性，不定长属性数）。
`Hash ---> {username:"zhangsan",age:"18",sex:"man"}`
如果hash中包含很少的字段，那么该类型的数据也将仅占用很少的磁盘空间。每一个hash可以存储4294967295个键值对。

### 常用命令
##### 赋值hset
`hset key field value`：为指定的key设定field/value对（键值对）。
例如：
```
127.0.0.1:6379> hset user uname zhangsan
(integer) 1
```

##### 赋值hmset
`hmset key field value[field2 value2 ...]`：设置key中的多个field/value对（键值对）。
例如：
```
127.0.0.1:6379> hmset user uname zhangsan age 18
OK
```

##### 取值hget
`hget key field`：获取指定的key中的field的值。
例如：
```
127.0.0.1:6379> hget user uname
"zhangsan"
```

##### 取值hmget
`hmget key fields：获取指定的key中的多个field的值。
例如：
```
127.0.0.1:6379> hmget user uname age
1) "zhangsan"
2) "18"
```

##### 取值hgetall
`hgetall key`：获取指定的key中的所有key-value。
例如：
```
127.0.0.1:6379> hgetall user
1) "uname"
2) "zhangsan"
3) "age"
4) "18"
```

##### 删除hdel
`hdel key field [field...]`：可以删除一个或多个字段，返回值是被删除的字段的个数（0表示删除的字段不存在）。
例如：
```
127.0.0.1:6379> hdel user password age
(integer) 1
```
如果一个hash用hdel将所有字段全部删除，那么这个hash整个也被删除了。

##### 删除del
`del key`：删除整个hash。
例如：
```
127.0.0.1:6379> del user
(integer) 1
```

### 扩展命令
##### 增加指定数值hincrby
`hincrby key field increment`：设置key中的field的值增加increment。
例如：
```
127.0.0.1:6379> hincrby user age 10
(integer) 10
```

##### 判断字段是否存在hexists
`hexists key field`：判断指定的key中的field是否存在。返回1表示存在，返回0表示不存在。
例如：
```
127.0.0.1:6379> hexists user username
(integer) 0
```

##### 获取key的字段个数hlen
`hlen key`：获取key所包含的field的数量。
例如：
```
127.0.0.1:6379> hlen user
(integer) 1
```

##### 获得所有的字段hkeys
`hkeys key`：获得所有的字段。
例如：
```
127.0.0.1:6379> hkeys user
1) "age"
```

##### 获得所有的字段的值hvals
`hvals key`：获得所有的字段对应的值。
例如：
```
127.0.0.1:6379> hvals user
1) "10"
```