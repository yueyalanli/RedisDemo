package com.redis.teacher;

import java.util.List;

/**
 * Created by lenovo on 2016/11/29.
 */
public interface TeacherInfoService {
    //批量增加老师信息
    boolean insertTeacherInfo(List<String> teacherID);
    //批量删除
    boolean delTeacherInfo(List<String> teacherID);
    //修改
    boolean editTeacherInfo(String teacherID);
    //查询
    List<String> selectTeacherInfo(List<String> teacherID);

}
