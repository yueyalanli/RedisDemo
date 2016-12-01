package com.redis.service.course.impl;

import com.redis.service.course.CourceInfoService;
import com.redis.service.model.CourseModel;
import com.redis.service.model.CourseScoreModel;
import com.redis.service.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.*;

/**
 * Created by lenovo on 2016/11/30.
 */
@Service("CourseInfoService")
public class CourseInfoServiceImpl implements CourceInfoService{
    @Autowired
    RedisUtils redisUtils;
    //新增信息  使用hash进行存储
    @Override
    public String insertStudentInfo(List<CourseModel> courseModels) {
        String infoFlag = null;
        ShardedJedis resource = null;
        try {
            resource = redisUtils.getShardedJeids();

            //将对应课程号的 课程信息存入相关的String类型里
            for(CourseModel model:courseModels){
                HashMap<String,String> courseMap = new HashMap<String, String>();
                courseMap.put("name",model.getName());
                courseMap.put("Tid",model.getTid());
                //使用HMSET(同时多个)对HASH结构存储课程信息
                infoFlag = resource.hmset(model.getCourseId(),courseMap);
                resource.sadd("course",model.getCourseId());
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return infoFlag;
    }

    @Override
    public List<Object> selectCourseInfo(List<String> courseIds) {
        ShardedJedis resource = null;
        List<Object> resultList = new ArrayList<Object>();
        Map<String, String> courseMap = new HashMap<String, String>();
        try {
            resource = redisUtils.getShardedJeids();
            for(String courseId:courseIds){
                //@@@@使用Hgetall对HASH结构存储课程信息
                courseMap = resource.hgetAll(courseId);
                resultList.add(courseMap);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            redisUtils.returnResource(resource);
        }
        return resultList;
    }
//为某课程录入成绩
    @Override
    public Long enteringScore(String courseId,List<CourseScoreModel> courseScoreList) {
        ShardedJedis resource = null;
        Long flag = 0L;
        String courseScoreId = courseId + "score";
        try {
            resource = redisUtils.getShardedJeids();
            for (CourseScoreModel model : courseScoreList) {
                //@@@@@将某门课程成绩存入有序集合中   课程号(courseId)为成绩表的Key值
                resource.zadd(courseScoreId, model.getCourseScore(), model.getSid());
            }
            //@@@@ hset（单个插入）将courseId存入对应的课程表信息中方便查询
            flag = resource.hset(courseId, "score", courseScoreId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtils.returnResource(resource);
        }
        return flag;
    }
    // 查询课程成绩排名  sort Set
    @Override
    public HashMap<String,Double> SearchRank(String courseId) {
        ShardedJedis resource = null;
        String courseScoreId = null;
        Set<String> rank =null;
        HashMap<String,Double> rankMap = new HashMap<String, Double>();
        try {
            resource = redisUtils.getShardedJeids();
            //@@@ HGET key field 获取存储在哈希表中指定字段的值
            courseScoreId = resource.hget(courseId,"score");
            //@@@zrange 根据courseScoreId  对成绩进行排序
            rank = resource.zrange(courseScoreId,0,-1); //得到的是Id号
            Iterator<String> sids = rank.iterator();
            //遍历出降序排列的  学号
            while (sids.hasNext())
            {
                String sid = sids.next();
            //   @@@zscore 返回有序集中，成员的分数值
                Double scoreBySid = resource.zscore(courseScoreId,sid);
                rankMap.put(sid,scoreBySid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtils.returnResource(resource);
        }
        return rankMap;
    }
//查询及格或不及格的人 ZREVRANGEBYSCORE
    @Override
    public Set<String> searchPass(String courseId,Double max, Double min) {
        ShardedJedis resource = null;
        String courseScoreId = null;
        Set<String> passnumbers = null;
        try {
            resource = redisUtils.getShardedJeids();
            courseScoreId = resource.hget(courseId,"score");
            passnumbers = resource.zrevrangeByScore(courseScoreId,max,min);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtils.returnResource(resource);
        }
        return passnumbers;
    }
    //查询及格的人 和 不及格的人数  @@zcount 分数值在 min 和 max 之间的成员的数量。
    @Override
    public Long searchPassNumber(String courseId, Double min, Double max) {
        ShardedJedis resource = null;
        String courseScoreId = null;
        Long passnumber = 0L;
        try {
            resource = redisUtils.getShardedJeids();
            courseScoreId = resource.hget(courseId,"score");
            //@@zcount
            passnumber = resource.zcount(courseScoreId,min,max);
            System.out.println(passnumber);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtils.returnResource(resource);
        }
        return passnumber;
    }
    //查询某个学生在本课程的排名（ZREVRANK 高到低）
    @Override
    public Long searchRankOfSid(String courseId, String id) {
        ShardedJedis resource = null;
        String courseScoreId = null;
        Long rank = 0L;
        try {
            resource = redisUtils.getShardedJeids();
            courseScoreId = resource.hget(courseId,"score");
            //@@zrevrank
            rank = resource.zrevrank(courseScoreId,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtils.returnResource(resource);
        }
        return rank;
    }


}
