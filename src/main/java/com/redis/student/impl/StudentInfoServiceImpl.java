package com.redis.student.impl;

import com.redis.student.StudentInfoService;
import com.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2016/11/29.
 */
@Service("StudentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService{
    @Autowired
    RedisUtils redisUtils;

    public boolean insertStudentInfo(List<String> teacherID) {
        redisUtils.getShardedJeids().set("ke","test");
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
