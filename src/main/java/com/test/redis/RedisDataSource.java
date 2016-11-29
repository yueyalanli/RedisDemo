package com.test.redis;

import redis.clients.jedis.ShardedJedis;

/**
 * Created by lenovo on 2016/11/28.
 */

public interface RedisDataSource {
    public abstract ShardedJedis getRedisClient();
    public void returnResource(ShardedJedis shardedJedis);
    public void returnResource(ShardedJedis shardedJedis,boolean broken);
}
