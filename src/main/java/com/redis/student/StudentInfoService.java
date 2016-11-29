package com.redis.student;

import java.util.List;

/**
 * Created by Judy on 2016/11/29.
 */
public interface StudentInfoService {
    //批量增加老师信息
    boolean insertStudentInfo(List<String> teacherID);
    //批量删除
    boolean delStudentInfo(List<String> teacherID);
    //修改
    boolean editStudentInfo(String teacherID);
    //查询
    List<String> selectStudentInfo(List<String> teacherID);
}
