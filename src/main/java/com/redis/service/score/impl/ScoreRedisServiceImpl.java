package com.redis.service.score.impl;

import com.redis.constant.Constant;
import com.redis.service.model.ScoreModel;
import com.redis.service.score.ScoreRedisService;
import com.redis.service.utils.RedisUtils;
import com.redis.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 杨阳
 * @Date 2016/11/30 - 16:27
 */
@Service
public class ScoreRedisServiceImpl implements ScoreRedisService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public Long addScore(List<ScoreModel> model) {
        ShardedJedis jedis = null;
        Long result = 0L;
        try {
            jedis = redisUtils.getShardedJeids();
            String[] str = new String[model.size()];
            int i = 0;
            for (ScoreModel score : model) {
                str[i++] = JsonUtils.writeToString(score);
            }
            /*******************采用lpush命令对list进行操作******************/
            result = jedis.lpush(Constant.SCORE_KEY, str);
        } finally {
            redisUtils.returnResource(jedis);
        }
        return result;
    }

    @Override
    public List<ScoreModel> selectScoreList() {
        ShardedJedis jedis = null;
        List<ScoreModel> modelList = new ArrayList<ScoreModel>();
        try {
            jedis = redisUtils.getShardedJeids();
            /*******************采用lrange命令对list进行操作******************/
            List<String> result = jedis.lrange(Constant.SCORE_KEY, 0, -1);
            for (String str : result) {
                ScoreModel model = JsonUtils.readValue(str, ScoreModel.class);
                modelList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtils.returnResource(jedis);
        }
        return modelList;
    }



    @Override
    public Long editScoreList(List<ScoreModel> modelList) {
        ShardedJedis jedis = null;
        Long result = 0L;
        try {
            jedis = redisUtils.getShardedJeids();

        } catch (Exception e) {

        } finally {
            redisUtils.returnResource(jedis);
        }
        return null;
    }

}
