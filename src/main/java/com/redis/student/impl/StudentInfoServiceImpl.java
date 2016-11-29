package com.redis.student.impl;

import com.redis.teacher.TeacherInfoService;

import java.util.List;

/**
 * Created by lenovo on 2016/11/29.
 */
public class StudentInfoServiceImpl implements TeacherInfoService {
    @Override
    public boolean insertTeacherInfo(List<String> teacherID) {
        return false;
    }

    @Override
    public boolean delTeacherInfo(List<String> teacherID) {
        return false;
    }

    @Override
    public boolean editTeacherInfo(String teacherID) {
        return false;
    }

    @Override
    public List<String> selectTeacherInfo(List<String> teacherID) {
        return null;
    }
}
