package com.redis.service.score;

import com.redis.service.model.ScoreModel;

import java.util.List;

/**
 * @Author 杨阳
 * @Date 2016/11/30 - 16:26
 */
public interface ScoreRedisService {
    //添加成绩
    Long addScore(List<ScoreModel> model);

    //获取成绩单
    List<ScoreModel> selectScoreList();

    //修改成绩 - 由于redis - list存储修改不方便，暂时废弃
    Long editScoreList(List<ScoreModel> modelList);

}
