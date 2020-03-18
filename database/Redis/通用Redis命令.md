## 通用redis命令
redis数据类型：`String`（字符串），`Hash`（哈希），`List`（列表），`Set`（集合），`Zset`（SortedSet：有序集合）。

#### 查询符合要求的键keys
`keys pattern`：获取所有与pattern匹配的key，返回所有与该pattern匹配的keys。
`pattern`：通配符：
`*`：表示0个或多个任意字符。
`?`：表示任意一个字符。
`keys * `：查询所有的key。
例如：
```
127.0.0.1:6379> keys *
 1) "zset1"
 2) "sunion"
 3) "password"
 4) "list1"
 5) "user"
 6) "set1"
 7) "list3"
 8) "num"
 9) "set2"
10) "sinter"
11) "sdiff"
12) "list2"
13) "visit_count"
```
例1：查询key长度是4的key名。
命令：`keys ????`
```
127.0.0.1:6379> keys ????
1) "user"
2) "set1"
3) "set2"
```
例2：查询key名中包含set的key。
命令：`keys \*set*`
```
127.0.0.1:6379> keys *set*
1) "zset1"
2) "set1"
3) "set2"
```

#### 删除指定的key
`del key1 key2 ...`：删除指定的key，不管key对应的值是什么类型都能删。
例如：
```
127.0.0.1:6379> del set1 num
(integer) 2
```

#### 判断key是否存在
`exists key`：判断指定的key是否存在。返回值为0或者1。0表示不存在，1表示存在。
例如：
```
127.0.0.1:6379> exists username
(integer) 0
127.0.0.1:6379> exists password
(integer) 1
```

#### 重命名key
`rename key newkey`：将指定的key重命名为newkey。
例如：
```
127.0.0.1:6379> keys *
 1) "zset1"
 2) "sunion"
 3) "password"
 4) "list1"
 5) "user"
 6) "list3"
 7) "set2"
 8) "sinter"
 9) "sdiff"
10) "list2"
11) "visit_count"
127.0.0.1:6379> rename password newpassword
OK
127.0.0.1:6379> keys *
 1) "zset1"
 2) "sunion"
 3) "list1"
 4) "user"
 5) "list3"
 6) "newpassword"
 7) "set2"
 8) "sinter"
 9) "sdiff"
10) "list2"
11) "visit_count"
```

#### 获取指定key的值类型
`type key`：获取指定key的值类型。该命令将以字符串的格式返回。返回的字符串为string、hash、list、set和zset。如果key不存在返回none。
例如：
```
127.0.0.1:6379> keys *
 1) "zset1"
 2) "sunion"
 3) "list1"
 4) "user"
 5) "list3"
 6) "newpassword"
 7) "set2"
 8) "sinter"
 9) "sdiff"
10) "list2"
11) "visit_count"
127.0.0.1:6379> type newpassword
string
127.0.0.1:6379> type user
hash
127.0.0.1:6379> type list1
list
127.0.0.1:6379> type set2
set
127.0.0.1:6379> type zset1
zset
127.0.0.1:6379> type abc
none
```

#### 设置key的有效/过期时间
`expire key`：设置指定的key的有效时间。单位：秒。
`ttl key`：获取指定的key的剩余有效时间。如果没有设置有效时间，默认永久有效，返回-1。返回-2表示已超时，key已经不存在。
如果某个key过期，redis会将其删除。
例如：

```
127.0.0.1:6379> expire newpassword 20
(integer) 1
127.0.0.1:6379> keys *
 1) "zset1"
 2) "sunion"
 3) "list1"
 4) "user"
 5) "list3"
 6) "newpassword"
 7) "set2"
 8) "sinter"
 9) "sdiff"
10) "list2"
11) "visit_count"
127.0.0.1:6379> ttl newpassword
(integer) 17
127.0.0.1:6379> ttl newpassword
(integer) 2
127.0.0.1:6379> ttl newpassword
(integer) -2
127.0.0.1:6379> ttl user
(integer) -1
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
```