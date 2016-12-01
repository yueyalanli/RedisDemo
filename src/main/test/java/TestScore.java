import com.redis.service.model.ScoreModel;
import com.redis.service.score.ScoreRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 杨阳
 * @Date 2016/12/1 - 11:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContext-redis.xml")
public class TestScore {

    @Autowired
    private ScoreRedisService scoreRedisService;

    @Test
    public void test(){
        List<ScoreModel> model = new ArrayList<ScoreModel>();
        ScoreModel model1 = new ScoreModel();
        model1.setName("yang");
        model1.setChinese(97);
        model1.setMath(67);
        model1.setEnglish(89);
        model.add(model1);
        ScoreModel model2 = new ScoreModel();
        model2.setName("qingju");
        model2.setChinese(97);
        model2.setMath(67);
        model2.setEnglish(89);
        model.add(model2);
        System.out.println(scoreRedisService.addScore(model));
    }

    @Test
    public void testselect(){
        System.out.println(scoreRedisService.selectScoreList());
    }


}
