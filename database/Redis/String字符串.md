## String
适合存储单一数值，验证码，PV，缓存字符串。
### 常用命令
##### 赋值set
`set key value`：设定key持有指定的字符串value，如果该key存在则进行覆盖操作。总是返回“OK”。
例如：
```
127.0.0.1:6379> set username zhangsan
OK
```

##### 取值get
`get key`：获取key的value。如果与该key关联的value不是String类型，redis将返回错误信息，因为get命令只能用于获取String类型的value；如果该key不存在，返回（nil）。
例如：
```
127.0.0.1:6379> get username
"zhangsan"
```

##### 删除del
`del key`：删除指定key。返回值是一个数字类型，表示删除了几条数据。
例如：
```
127.0.0.1:6379> del username
(integer) 1
```

### 扩展命令
##### 先获取值后设置值getset
`getset key value`：先获取该key的值，然后再设置该key的值。
例如：
```
127.0.0.1:6379> getset username lisi
(nil)
127.0.0.1:6379> get username
"lisi"
```

##### 数字值加1操作incr
`incr key`：将指定的key的value原子性的递增1，如果该key不存在，其初始值为0，在incr之后其值为1。如果value的值不能转成整数，如hello，该操作将执行失败并返回相应错误信息。（相当于++i）
例如：
```
127.0.0.1:6379> incr visit_count
(integer) 1
```

##### 数字值减1操作decr
`decr key`：将指定的key的value原子性的递减1，如果该key不存在，其初始值为，在decr之后其值为-1。如果value的值不能转成整数，如hello，该操作将执行失败并返回相应错误信息。（相当于--i）
例如：
```
127.0.0.1:6379> decr visit_count
(integer) 0
```

##### 字符串拼凑append
`append key value`：拼凑字符串。如果该key存在，则在原有的value后追加该值； 如果该key不存在，则重新创建一个key/value。与set命令不同的地方是：set命令如果key存在，则会将value覆盖，而append会在尾部追 加。
例如：
```
127.0.0.1:6379> append password 123
(integer) 3
```
此时password为123。
```
127.0.0.1:6379> append password 456
(integer) 6
```
此时password为123456。

##### 数字值自增任意值操作incrby
`incrby key increment`：将指定的key的value原子性增加increment，如果该key不存在，其初始值为0，在incrby之后其值为increment。如果value的值不能转成整数，如hello，该操作将执行失败并返回相应错误信息。
例如：
```
127.0.0.1:6379> incrby num 10
(integer) 10
```
此时num为10。

##### 数字值自增任意值操作decrby
`decrby key decrement`：将指定的key的value原子性减少decrement，如果该key不存在，其初始值为0，在decrby之后其值为decrement。如果value的值不能转成整数，如hello，该操作将执行失败并返回相应错误信息。
例如：
```
127.0.0.1:6379> decrby num 5
(integer) 5
```
此时num为5。