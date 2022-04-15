## redis集群配置
#### `daemonize yes`：修改服务为后台启动

#### `port 9001`：端口号

#### `dir /usr/local/software/redis-cluster/9001`：设置数据持久化文件存放目录

#### `cluster-enabled yes`：开启集群模式

#### `cluster-config-file nodes-9001.conf`：指定集群节点配置文件
官方解释：Every cluster node has a cluster configuration file. This file is not intended to be edited by hand. It is created and updated by Redis nodes.Every Redis Cluster node requires a different cluster configuration file.Make sure that instances running in the same system do not have overlapping cluster configuration file names.
翻译：每个集群节点都有一个集群配置文件。这个文件不是打算手工编辑。它是由Redis节点创建和更新的。每个Redis集群节点都需要一个不同的集群配置文件。确保在同一系统中运行的实例没有重叠的集群配置文件名。

#### `cluster-node-timeout 15000`：配置集群节点超时时间

#### `appendonly yes`：开启AOF持久化机制

#### `pidfile /var/run/redis_9001.pid`：指定不同的pid文件

#### `logfile "/usr/local/software/redis-cluster/9001/redis.log"`：指定log日志文件

#### `protected-mode no` 非保护模式

vim批量替换操作：1,$s/被替换的内容/替换后的内容/g

## 创建集群节点
`redis-cli --cluster create host1:port1 ... hostN:portN --cluster-replicas <arg>`

在`create`后列出所有需要加入到集群中的redis服务器IP+端口。

`--cluster-replicas`：主从服务器比例。主/从。

#### 设置密码
给redis cluster集群加上认证，登录到redis节点执行下面的操作

[root@redis65 /]# redis-cli -h 192.168.5.65 -p 6001 -c
> config set masterauth zxc789
> config set requirepass zxc789
> auth zxc789
> config rewrite
> 
集群命令：
/usr/local/software/redis/bin/redis-cli --cluster create 192.168.169.128:9001 192.168.169.128:9002 192.168.169.128:9003 192.168.169.128:9004 192.168.169.128:9005 192.168.169.128:9006 --cluster-replicas 1

带密码登录：
`redis-cli -c -h 192.168.169.132 -p 6379 -a password`

6台虚拟机集群
```
./bin/redis-cli --cluster create 192.168.169.130:6379 192.168.169.131:6379 192.168.169.132:6379 192.168.169.133:6379 192.168.169.134:6379 192.168.169.135:6379 --cluster-replicas 1
```