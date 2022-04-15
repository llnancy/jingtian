## List
适用于做大数据集合的增删操作，任务队列。

### 常用命令
##### 赋值lpush和rpush
`lpush key values[value1 value2...]`：在指定的key所关联的list的头部插入所有的values，如果该key不存在，该命令在插入的之前创建一个与该key关联的空链表，之后再向该链表的头部插入数据，插入成功，返回元素的个数。
`rpush key values[value1 value2...]`：在该list的尾部添加元素。与lpush添加元素的方向相反。
例如：

```
127.0.0.1:6379> lpush list1 a b c d
(integer) 4
```
结果为`list1：[d,c,b,a]`
```
127.0.0.1:6379> rpush list2 a b c d
(integer) 4
```
结果为`list2：[a,b,c,d]`
```
127.0.0.1:6379> rpush list3 a b c a b c a b
(integer) 8
```
结果为`list3：[a,b,c,a,b,c,a,b,c,a,b]`

##### 查看列表lrange
`lrange key start end`：获取链表中从start到end的元素的值，start和end从0开始计数；也可为负数，若为-1则表示链表尾部的元素，-2则表示倒数第二个，以此类推...。
例如：
```
127.0.0.1:6379> lrange list1 0 -1
1) "d"
2) "c"
3) "b"
4) "a"
```
显示链表中的全部元素。

##### 两端弹出（实质删除）lpop和rpop
`lpop key`：返回并弹出指定的key关联的链表的第一个元素，即头部元素。如果该key不存在，返回nil；若key存在，则返回链表的头部元素。
`rpop key`：返回并弹出指定的key关联的链表的尾部元素。
例如：
```
127.0.0.1:6379> lpop list1
"d"
```
结果为`list1：[c,b,a]`
```
127.0.0.1:6379> rpop list1
"a"
```
结果为`list1：[c,b]`
注意：若元素弹出后list链表为空，则该list将被删除。

##### 删除del
`del key`：通用的删除命令。

### 扩展命令
##### 获取list中元素的个数llen
`llen key`：返回指定的key关联的链表中的元素的数量。
例如：
```
127.0.0.1:6379> llen list3
(integer) 8
```

##### 删除某种元素lrem（效率极为低下）
`lrem key count value`：删除count个值为value的元素，如果count大于0，从头向尾遍历并删除count个值为value的元素；如果count小于0，则从尾向头遍历并删除；如果count等于0，则删除链表中所有值为value的元素。
例如：
```
127.0.0.1:6379> lrem list3 2 a
(integer) 2
```
从头向尾遍历list3并删除2个值为a的元素。结果为`list3：[b,c,b,c,a,b,c,a,b]`
```
127.0.0.1:6379> lrem list3 0 b
(integer) 3
```
删除list3中所有值为b的元素。结果为`list3：[c,c,a,c,a]`

##### 通过索引替换元素lset（效率不高）
`lset key index value`：设置指定key的链表中下标索引为index的元素值为value。0代表链表的头元素，-1代表链表的尾元素。如果下标索引不存在则抛出异常。
例如：
```
127.0.0.1:6379> lset list2 1 zzz
OK
```
将list2中下标索引为1的元素值替换为zzz。结果为`list2：[a,zzz,c,d]`

##### 在索引前/后插入元素（效率不高）
`linsert key before/after pivot value`：在pivot元素前/后插入value这个元素。返回list的元素个数。

例如：
```
127.0.0.1:6379> linsert list2 before c ggg
(integer) 5
```
在c元素前插入ggg元素。结果为`list2：[a,zzz,ggg,c,d]`
```
127.0.0.1:6379> linsert list2 after c hhh
(integer) 6
```
在c元素后插入hhh元素。结果为`list2：[a,zzz,ggg,c,hhh,d]`

##### 循环操作
`rpoplpush resource destination`：将链表中的尾部元素弹出并添加到头部。[循环操作]

两个队列进行排队：

```
127.0.0.1:6379> lpush list1 a b c d
(integer) 4
127.0.0.1:6379> rpoplpush list1 list2
"a"
127.0.0.1:6379> lrange list1 0 -1
1) "d"
2) "c"
3) "b"
127.0.0.1:6379> lrange list2 0 -1
1) "a"
```

循环队列：
```
127.0.0.1:6379> lrange list1 0 -1
1) "d"
2) "c"
3) "b"
127.0.0.1:6379> rpoplpush list1 list1
"b"
127.0.0.1:6379> rpoplpush list1 list1
"c"
127.0.0.1:6379> rpoplpush list1 list1
"d"
127.0.0.1:6379> lrange list1 0 -1
1) "d"
2) "c"
3) "b"
```