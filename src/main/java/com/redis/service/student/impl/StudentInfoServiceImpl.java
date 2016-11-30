package com.redis.service.student.impl;

import com.redis.service.model.StudentModel;
import com.redis.service.student.StudentInfoService;
import com.redis.service.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.List;

/**
 * Created by lenovo on 2016/11/29.
 */
@Service("StudentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService{
    @Autowired
    RedisUtils redisUtils;
    //对学生信息 做批量插入操作
    public Long insertStudentInfo(List<StudentModel> studentModels) {
        Long insertFlag = 0L;
        Long infoFlag;
        String Sids = null;
        ShardedJedis resource = null;
        try {
            resource = redisUtils.getShardedJeids();

            //将对应学号的 学生信息存入相关的String类型里
            for(StudentModel model:studentModels){
                String studentInfo = new StringBuffer().append(model.getName()).append(","+model.getSex()).append(","+model.getClasses()).toString();
                //@@@@@@@@对字符串类型做setnx(当key不存在时插入)操作
                infoFlag = resource.setnx(model.getSid(),studentInfo);
                System.out.println("infoFlag:"+infoFlag);
                //插入学生信息成功时，将ID记录到Set
                if(1L == infoFlag){
                    //@@@@@@对Set类型做sadd操作
                    insertFlag = resource.sadd("student",model.getSid());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return insertFlag;
    }

    //对学生信息  做批量删除操作
    @Override
    public Long delStudentInfo(List<String> Sids) {
        Long delFlag = 0L;
        Long delInfoBySid = 0L;
        ShardedJedis resource = null;
        try{
            resource = redisUtils.getShardedJeids();
            //@@@@@@对Set集合 进行删除操作
            for(String sid:Sids) {
                //@@@@从Set集合中删除学号
                delFlag = resource.srem("student", sid);
                System.out.println("delFlag:" + delFlag);
                //如果删除成功，同时删除对应的 key=sid
                if (1L == delFlag) {
                    //@@@@使用String类型的del
                    delInfoBySid = resource.del(sid);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return delFlag;
    }

    @Override
    public boolean editStudentInfo(String Sid,String editContent) {
        ShardedJedis resource = null;
        try{
            resource =  redisUtils.getShardedJeids();
            //@@@@@使用Set命令
            resource.set(Sid,editContent);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return false;
    }

    @Override
    public List<String> selectStudentInfo(List<String> teacherID) {
        return null;
    }
}
