package com.redis.student.impl;

import com.redis.student.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

/**
 * Created by lenovo on 2016/11/29.
 */
@Service("StudentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService{
    @Autowired
    ShardedJedisPool shardedJedisPool;

    public boolean insertStudentInfo(List<String> teacherID) {
        return true;
    }

    @Override
    public boolean delStudentInfo(List<String> teacherID) {
        return false;
    }

    @Override
    public boolean editStudentInfo(String teacherID) {
        return false;
    }

    @Override
    public List<String> selectStudentInfo(List<String> teacherID) {
        return null;
    }
}
