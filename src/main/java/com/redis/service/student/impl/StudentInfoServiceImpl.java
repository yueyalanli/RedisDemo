package com.redis.service.student.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.service.model.StudentModel;
import com.redis.service.student.StudentInfoService;
import com.redis.service.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
                ObjectMapper mapper = new ObjectMapper();
                String studentInfo = mapper.writeValueAsString(model);
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
            //对Set集合 进行删除操作
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
    //修改某学生信息
    @Override
    public String editStudentInfo(String Sid,String editContent) {
        String editFlag = null;
        ShardedJedis resource = null;
        try{
            resource =  redisUtils.getShardedJeids();
            //@@@@ShareJedis不支持修改 renamenx(old,new)的值，Jedis可以做此操作
//            Jedis jedis = new Jedis("172.16.2.234",6379);
//            jedis.renamenx();
            //@@@@@使用Set命令 修改学生信息
            editFlag = resource.set(Sid,editContent);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return editFlag;
    }
//对学生信息做批量查询
    @Override
    public HashMap<String,String> selectStudentInfoByIds(List<String> Sids){
        HashMap<String,String> studentHash = new HashMap<String, String>();
        ShardedJedis resource = null;
        try{
            resource = redisUtils.getShardedJeids();
            for(String sid:Sids) {
                //@@@@使用get获取对应学生的信息
                String StudentInfoById = resource.get(sid);
                studentHash.put(sid,StudentInfoById);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return studentHash;
    }
    //获取所有的学生信息
    @Override
    public HashMap<String, String> selectAllStudentInfo() {
        HashMap<String, String> allStudent = new HashMap<String, String>();
        ShardedJedis resource = null;
        try{
            resource = redisUtils.getShardedJeids();
            //@@@@使用smembers命令获取所有学生学号
            Set<String> sids = resource.smembers("student");
            for(String sid:sids){
                String studentInfo = resource.get(sid);
                allStudent.put(sid,studentInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return allStudent;
    }
//查询某学生是否存在
    @Override
    public boolean ifExitsts(String sid) {
        ShardedJedis resource = null;
        boolean flag = false;
        try{
            resource = redisUtils.getShardedJeids();
            flag = resource.exists(sid);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return flag;
    }

    @Override
    public List<String> LikeSearch(String keyLike) {
        ObjectMapper mapper = new ObjectMapper();
        ShardedJedis resource = null;
        List<String> result = null;
        try{
            resource = redisUtils.getShardedJeids();
            //使用 sscan 命令 迭代集合中的元素
            result = resource.sscan("student",keyLike).getResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return result;
    }

}
