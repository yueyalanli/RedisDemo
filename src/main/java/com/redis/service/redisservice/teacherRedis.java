package com.redis.service.redisservice;

import com.redis.service.model.TeacherModel;
import com.redis.service.utils.RedisUtils;
import redis.clients.jedis.ShardedJedis;

/**
 * @Author 杨阳
 * @Date 2016/11/29 - 16:43
 */
public class teacherRedis {

    RedisUtils redisUtils = new RedisUtils();

    private void addTeacher(TeacherModel model){
        ShardedJedis jedis = null;
        try{
            jedis = redisUtils.getShardedJeids();
            jedis.sadd("teacher",model.getTId());

        }finally {
            redisUtils.returnResource(jedis);
        }
    }

}

