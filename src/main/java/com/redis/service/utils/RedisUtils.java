package com.redis.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by lenovo on 2016/11/29.
 */
@Service
public class RedisUtils {
    @Autowired
    public ShardedJedisPool shardedJedisPool;

    public ShardedJedis getShardedJeids(){
        ShardedJedis jds = null;
        try{
            jds = shardedJedisPool.getResource();
            return jds;
        }catch (Exception e){
            e.printStackTrace();
        }
        return jds;
    }

    public void returnResource(ShardedJedis shardedJedis){
        if(shardedJedis != null){
            shardedJedisPool.returnResource(shardedJedis);
        }
    }
}
