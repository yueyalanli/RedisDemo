package com.redis.service.classes;

import java.util.List;
import java.util.Set;

/**
 * Created by Judy on 2016/11/29.
 */
public interface ClassesInfoService {
    //新增班级
    Long insertClasses(String cid);
    //往班级添加成员
    Set addMemberstoClass(String cid, List<String> sids);
//    //合并两个班级
//    Set UnionClasses(String cid1,String cid2);
}
