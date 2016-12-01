package com.redis.service.course;

import com.redis.service.model.CourseModel;
import com.redis.service.model.CourseScoreModel;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Judy on 2016/11/29.
 */
public interface CourceInfoService {
    //新增课程
    String insertStudentInfo(List<CourseModel> courseIds);
    //批量获取某门课程的所有信息
    List<Object> selectCourseInfo(List<String> courseIds);
    //为某课程录入成绩
    Long enteringScore(String courseId,List<CourseScoreModel> courseScoreList);
    //查询某门课程成绩排名
    HashMap<String,Double> SearchRank(String courseId);
    //查询及格的人 和 不及格的学生
    Set<String> searchPass(String courseId,Double score1,Double score2);
    //查询及格的人 和 不及格的人数
    Long searchPassNumber(String courseId,Double score1,Double score2);
    //查询某个学生在本课程的排名（ZREVRANK 高到低）
    Long searchRankOfSid(String courseId,String id);
}
