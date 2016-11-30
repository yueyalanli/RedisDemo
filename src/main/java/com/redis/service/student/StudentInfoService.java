package com.redis.service.student;

import com.redis.service.model.StudentModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Judy on 2016/11/29.
 */
public interface StudentInfoService {
    //批量增加老师信息
    Long insertStudentInfo(List<StudentModel> Sids);
    //批量删除
    Long delStudentInfo(List<String> Sids);
    //修改
    String editStudentInfo(String Sid,String editContent);
    //查询
    HashMap<String,String> selectStudentInfoByIds(List<String> Sids);
    //获取所有学生信息
    HashMap<String,String> selectAllStudentInfo();
    //查询某学生是否存在
    boolean ifExitsts(String sid);

}
