package com.redis.service.model;

import lombok.Data;

import java.util.List;

/**
 * Created by lenovo on 2016/11/30.
 */
@Data
public class ClassesModel {
    private String cid;  //班级号
    private String classTutor;  //姓名
    private String number;//班级人数
    private List<String> students ; //班级成员
}
