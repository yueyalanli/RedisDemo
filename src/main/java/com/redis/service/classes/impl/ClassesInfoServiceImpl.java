package com.redis.service.classes.impl;

import com.redis.service.classes.ClassesInfoService;
import com.redis.service.student.StudentInfoService;
import com.redis.service.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 2016/11/30.
 */
@Service("ClassesInfoService")
public class ClassesInfoServiceImpl implements ClassesInfoService{
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    StudentInfoService studentservice;
    //新增班级 信息
    @Override
    public Long insertClasses(String cid) {
        Long flag = 0L;
        ShardedJedis resource = null;
        try{
            resource = redisUtils.getShardedJeids();
            flag = resource.sadd("classes",cid);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return flag;
    }
//往某班级添加成员，并返回班级成员，Set 存班级成员信息
    @Override
    public Set addMemberstoClass(String cid,List<String> sids) {
        ShardedJedis resource = null;
        Set reviewIds = null;
        try{
            resource = redisUtils.getShardedJeids();
            resource.sadd(cid,sids.toString());
            //@@@@@@smemsers插入完成后，返回班级所有成员
            reviewIds = resource.smembers(cid);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return reviewIds;
    }


}
