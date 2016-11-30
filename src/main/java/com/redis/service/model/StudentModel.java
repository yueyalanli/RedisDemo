package com.redis.service.model;

import lombok.Data;

/**
 * Created by lenovo on 2016/11/29.
 */
@Data
public class StudentModel {
    private String Sid;  //学号
    private String name;  //姓名
    private String sex;//性别
    private String classes; //所属班级
}
