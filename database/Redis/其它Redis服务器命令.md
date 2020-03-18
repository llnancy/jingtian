## 其它redis服务器命令
#### ping命令
`ping`：测试连接是否存活。存活返回`PONG`
```
127.0.0.1:6379> ping
PONG
```

#### echo命令
`echo`：在命令行打印一些内容。
```
127.0.0.1:6379> echo content
"content"
```

#### select命令
`select 数据库名`：选择数据库。redis数据库编号从0~15，我们可以选择任意一个数据库来进行数据的存取。
```
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> select 15
OK
127.0.0.1:6379[15]> select 16
(error) ERR DB index is out of range
```

#### quit命令
`quit`：退出客户端连接。也可按Ctrl+C键。

#### dbsize命令
`dbsize`：返回当前数据库中key的数目。
```
127.0.0.1:6379> dbsize
(integer) 9
```

#### info命令
`info`：查看redis服务器的信息和统计。
```
127.0.0.1:6379> info
# Server 服务端配置
redis_version:5.0.3
redis_git_sha1:00000000
redis_git_dirty:0
redis_build_id:5cc6ab19b1913347
redis_mode:standalone
os:Linux 3.10.0-693.el7.x86_64 x86_64
arch_bits:64
multiplexing_api:epoll
atomicvar_api:atomic-builtin
gcc_version:4.8.5
# pid 可使用kill -9 杀死
process_id:995
run_id:e99499a67e6e5d46c66a0eed5e105698670549ba
# 端口
tcp_port:6379
uptime_in_seconds:39855
uptime_in_days:0
hz:10
configured_hz:10
lru_clock:7009991
executable:/usr/local/software/redis/./bin/redis-server
# 服务器启动加载的配置文件
config_file:/usr/local/software/redis/./redis.conf

# Clients 客户端信息
# 客户端连接数量
connected_clients:2
client_recent_max_input_buffer:2
client_recent_max_output_buffer:0
blocked_clients:0

# Memory 内存信息
used_memory:877192
used_memory_human:856.63K
used_memory_rss:8220672
used_memory_rss_human:7.84M
used_memory_peak:917880
used_memory_peak_human:896.37K
used_memory_peak_perc:95.57%
used_memory_overhead:858272
used_memory_startup:791024
used_memory_dataset:18920
used_memory_dataset_perc:21.96%
allocator_allocated:911928
allocator_active:1110016
allocator_resident:3461120
total_system_memory:1023688704
total_system_memory_human:976.27M
used_memory_lua:37888
used_memory_lua_human:37.00K
used_memory_scripts:0
used_memory_scripts_human:0B
number_of_cached_scripts:0
maxmemory:0
maxmemory_human:0B
maxmemory_policy:noeviction
allocator_frag_ratio:1.22
allocator_frag_bytes:198088
allocator_rss_ratio:3.12
allocator_rss_bytes:2351104
rss_overhead_ratio:2.38
rss_overhead_bytes:4759552
mem_fragmentation_ratio:9.85
mem_fragmentation_bytes:7385736
mem_not_counted_for_evict:0
mem_replication_backlog:0
mem_clients_slaves:0
mem_clients_normal:66616
mem_aof_buffer:0
mem_allocator:jemalloc-5.1.0
active_defrag_running:0
lazyfree_pending_objects:0

# Persistence
loading:0
rdb_changes_since_last_save:0
rdb_bgsave_in_progress:0
rdb_last_save_time:1550512812
rdb_last_bgsave_status:ok
rdb_last_bgsave_time_sec:0
rdb_current_bgsave_time_sec:-1
rdb_last_cow_size:4456448
aof_enabled:0
aof_rewrite_in_progress:0
aof_rewrite_scheduled:0
aof_last_rewrite_time_sec:-1
aof_current_rewrite_time_sec:-1
aof_last_bgrewrite_status:ok
aof_last_write_status:ok
aof_last_cow_size:0

# Stats
total_connections_received:11
total_commands_processed:562
instantaneous_ops_per_sec:0
total_net_input_bytes:18015
total_net_output_bytes:16635
instantaneous_input_kbps:0.00
instantaneous_output_kbps:0.00
rejected_connections:0
sync_full:0
sync_partial_ok:0
sync_partial_err:0
expired_keys:6
expired_stale_perc:0.00
expired_time_cap_reached_count:0
evicted_keys:0
keyspace_hits:339
keyspace_misses:9
pubsub_channels:0
pubsub_patterns:0
latest_fork_usec:394
migrate_cached_sockets:0
slave_expires_tracked_keys:0
active_defrag_hits:0
active_defrag_misses:0
active_defrag_key_hits:0
active_defrag_key_misses:0

# Replication
role:master
connected_slaves:0
master_replid:ec69156cf7f8b8d0c33228b89dfd950de0a9934f
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

# CPU CPU状态
used_cpu_sys:28.200501
used_cpu_user:29.310175
used_cpu_sys_children:0.687965
used_cpu_user_children:0.009667

# Cluster
cluster_enabled:0

# Keyspace 统计数据库中的键值对数量等
db0:keys=9,expires=0,avg_ttl=0
db1:keys=2,expires=0,avg_ttl=0
```

#### flushdb命令（慎用）
`flushdb`：删除当前选择数据库中的所有key。

#### flushall命令（慎用）
`flushall`：删除所有数据库中的所有key。