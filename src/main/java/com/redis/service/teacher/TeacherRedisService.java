package com.redis.service.teacher;


import com.redis.service.model.TeacherModel;

import java.util.List;
import java.util.Map;

/**
 * @Author 杨阳
 * @Date 2016/11/29 - 17:36
 */
public interface TeacherRedisService {
      //增加老师信息-批量
      Long addTeacherList(List<TeacherModel> modelList);

      //获取所有老师信息
      List<TeacherModel> getAllTeahcer();

      //获取老师信息-批量
      List<TeacherModel> getTeacherListByTIds(List<String> tids);

      //删除老师信息-批量
      Long deleteTeacherList(List<String> tids);

      //清空老师信息
      Long emptyTeacherList();

      //编辑老师信息-单条
      Long editTeacherList(Map<String,TeacherModel> map) ;
}
