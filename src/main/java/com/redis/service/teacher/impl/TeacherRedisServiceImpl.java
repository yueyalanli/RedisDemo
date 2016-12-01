package com.redis.service.teacher.impl;

import com.redis.constant.Constant;
import com.redis.service.model.TeacherModel;
import com.redis.service.teacher.TeacherRedisService;
import com.redis.service.utils.RedisUtils;
import com.redis.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author 杨阳
 * @Date 2016/11/29 - 17:37
 */
@Service
public class TeacherRedisServiceImpl implements TeacherRedisService {


    @Autowired
    RedisUtils redisUtils;

    //添加老师信息-批量
    @Override
    public Long addTeacherList(List<TeacherModel> modelList) {
        ShardedJedis jedis = null;
        Long result1;
        try {
            jedis = redisUtils.getShardedJeids();
            String[] str = new String[modelList.size()];
            int i = 0;
            for (TeacherModel model : modelList) {
                /*******************采用set命令对String进行操作******************/
                String result = jedis.set(model.getTId(), JsonUtils.writeToString(model));
                if (result.equals(Constant.TEACHER_SUCCESS_STATUS)) {
                    str[i++] = model.getTId();
                }
            }
            /*******************采用sadd命令对Set进行操作******************/
            result1 = jedis.sadd(Constant.TEACHER_KEY, str);
        } finally {
            redisUtils.returnResource(jedis);
        }
        return result1;
    }

    //获取所有老师信息
    @Override
    public List<TeacherModel> getAllTeahcer() {
        ShardedJedis jedis = null;
        List<TeacherModel> modelList = new ArrayList<TeacherModel>();
        try {
            jedis = redisUtils.getShardedJeids();
            /*******************采用smembers命令对Set进行操作******************/
            Set<String> result = jedis.smembers(Constant.TEACHER_KEY);
            List<String> keys = new ArrayList<String>();
            keys.addAll(result);
            modelList = getTeacherListByTIds(keys);
        } finally {
            redisUtils.returnResource(jedis);
        }
        return modelList;
    }

    //根据教师编号获取老师信息列表
    @Override
    public List<TeacherModel> getTeacherListByTIds(List<String> tids) {
        ShardedJedis jedis = null;
        List<TeacherModel> modelList = new ArrayList<TeacherModel>();
        try {
            jedis = redisUtils.getShardedJeids();
            for (String key : tids) {
                /*******************采用get命令对String进行操作******************/
                String result = jedis.get(key);
                if (!StringUtils.isEmpty(result)) {
                    TeacherModel model = JsonUtils.readValue(result, TeacherModel.class);
                    modelList.add(model);
                }
            }
        } finally {
            redisUtils.returnResource(jedis);
        }
        return modelList;
    }

    //删除老师信息-批量
    @Override
    public Long deleteTeacherList(List<String> tids) {
        ShardedJedis jedis = null;
        Long result = 0L;
        try {
            jedis = redisUtils.getShardedJeids();
            for (String key : tids) {
                /*******************采用del命令对Set进行操作******************/
                if (jedis.del(key) == 1L) {
                    /*******************采用srem命令对Set进行操作******************/
                    result = jedis.srem(Constant.TEACHER_KEY, key);
                }
            }
        } finally {
            redisUtils.returnResource(jedis);
        }
        return result;
    }

    //清空所有教师信息
    @Override
    public Long emptyTeacherList() {
        ShardedJedis jedis = null;
        Long result = 0L;
        try {
            jedis = redisUtils.getShardedJeids();
            /*******************采用smembers命令对Set进行操作******************/
            Set<String> result1 = jedis.smembers(Constant.TEACHER_KEY);
            List<String> keys = new ArrayList<String>();
            keys.addAll(result1);
            result = deleteTeacherList(keys);
        } finally {
            redisUtils.returnResource(jedis);
        }
        return result;
    }

    //对教师信息进行编辑-批量
    @Override
    public Long editTeacherList(Map<String, TeacherModel> map) {
        ShardedJedis jedis = null;
        Long result = 0L;
        try {
            jedis = redisUtils.getShardedJeids();
            for (String key : map.keySet()) {
                /*******************采用set命令对String进行操作******************/
                String rst = jedis.set(key, JsonUtils.writeToString(map.get(key)));
                if (rst.equals(Constant.TEACHER_SUCCESS_STATUS)) {
                    result = 1L;
                }
            }
        } finally {
            redisUtils.returnResource(jedis);
        }
        return result;
    }

}
