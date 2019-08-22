## Set
redis的set是string类型的无序集合。
集合是通过哈希表实现的，所以添加、删除、查找的时间复杂度都是O（1）。
适用于大数据集合的并集、交集和差集运算。点赞点踩，抽奖，已读，共同好友等。
与List列表类型不同的是：Set集合中不允许出现重复的元素，这一点和c++标准库中的set容器是完全相同的。换句话说，如果多次添加相同元素，Set中将仅保留该元素的一个拷贝。和List类型相比，Set类型在功能上还存在着一个重要特性：即在服务端完成多个set集合之间的聚合计算操作，如集合的交并补，由于这些操作都在服务端完成，效率极高，节省了大量的网络IO消耗。

### 常用命令
##### 添加元素sadd
`sadd key values[value1 value2...]`：向set中添加元素。如果该key对应元素已存在则不会重复添加。
例如：
```
127.0.0.1:6379> sadd set1 a b c d a
(integer) 4
```
集合set1中的元素只有4个：`[a b c d]`

##### 删除元素srem
`srem key members[member1 member2...]`：删除set中指定的元素。返回删除的元素个数。
例如：
```
127.0.0.1:6379> srem set1 b d
(integer) 2
127.0.0.1:6379> srem set1 b d
(integer) 0
```

##### 查看元素smembers
`smembers key`：获取指定key中的所有元素。
例如：
```
127.0.0.1:6379> smembers set1
1) "a"
2) "c"
```

##### 判断元素是否存在sismember
`sismember key member`：判断参数中指定的成员是否在该set中，1表示存在（true），0表示不存在（false）或者该key本来就不存在。（判断的时间复杂度为O（1））
例如：
```
127.0.0.1:6379> sismember set1 a
(integer) 1
```

##### 集合运算：差集运算sdiff
`sdiff key1 key2 ...`：返回属于key1但不属于key2（或更多key）的元素。即差集运算。
例如：
```
127.0.0.1:6379> del set1
(integer) 1
127.0.0.1:6379> sadd set1 a b c d
(integer) 4
127.0.0.1:6379> sadd set2 b e
(integer) 2
127.0.0.1:6379> sdiff set1 set2
1) "c"
2) "d"
3) "a"
```
set1：`[a b c d]`
set2：`[b e]`
属于set1但不属于set2的元素：`[a c d]`

##### 集合运算：交集运算sinter
`sinter key1 key2 ...`：返回既属于key1又属于key2（或更多key）的元素。即交集运算。
例如：
```
127.0.0.1:6379> sinter set1 set2
1) "b"
```
set1：`[a b c d]`
set2：`[b e]`
既属于set1又属于set2的元素：`[b]`

##### 集合运算：并集运算sunion
`sunion key1 key2 ...`：返回属于key1或者属于key2（或更多key）的元素。即并集运算。
例如：
```
127.0.0.1:6379> sunion set1 set2
1) "b"
2) "d"
3) "a"
4) "c"
5) "e"
```
set1：`[a b c d]`
set2：`[b e]`
属于key1或者属于key2的元素：`[a b c d e]`

### 扩展命令
##### 获取集合的元素个数scard
`scard key`：获取set集合中元素的个数。
例如：
```
127.0.0.1:6379> scard set1
(integer) 4
```

##### 获取一个随机的元素srandmember
`srandmember key [count]`：随机返回set中的1个或count（count参数不是必须，不写则返回1个）个元素。
例如：
```
127.0.0.1:6379> srandmember set1
"c"
127.0.0.1:6379> srandmember set1 2
1) "b"
2) "d"
127.0.0.1:6379> srandmember set1 2
1) "d"
2) "a"
```
适用于随机抽奖等场景。

##### 集合运算扩展：将集合运算的结果存储在另一个集合中
`sdiffstore destination key[key...]`：将返回的差集存储在destination上。
例如：
```
127.0.0.1:6379> sdiffstore sdiff set1 set2
(integer) 3
```
set1：`[a b c d]`
set2：`[b e]`
差集结果：`[a c d]`
sdiff：`[a c d]`

`sinterstore destination key[key...]`：将返回的交集存储在destination上。
例如：
```
127.0.0.1:6379> sinterstore sinter set1 set2
(integer) 1
```
set1：`[a b c d]`
set2：`[b e]`
交集结果：`[b]`
sinter：`[b]`

`sunionstore destination key[key...]`：将返回的并集存储在destination上。
例如：
```
127.0.0.1:6379> sunionstore sunion set1 set2
(integer) 5
```
set1：`[a b c d]`
set2：`[b e]`
并集结果：`[a b c d e]`
sunion：`[a b c d e]`

将集合运算的结果存储在一个新的集合中，避免进行重复的集合运算。