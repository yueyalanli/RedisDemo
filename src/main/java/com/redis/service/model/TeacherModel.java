package com.redis.service.model;

import lombok.Data;

/**
 * @Author 杨阳
 * @Date 2016/11/29 - 16:38
 */
@Data
public class TeacherModel {
    private String name;  //姓名
    private String tId;  //老师id
    private String course;//课程
    private String sex; //性别
}
