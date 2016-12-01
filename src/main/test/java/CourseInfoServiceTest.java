import com.redis.service.course.CourceInfoService;
import com.redis.service.model.CourseModel;
import com.redis.service.model.CourseScoreModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContext-redis.xml")
public class CourseInfoServiceTest {
    @Autowired
    private CourceInfoService courceInfoService;
//新增课程
    @Test
    public void insertCourseInfoTest(){
        List<CourseModel> modelList = new ArrayList<CourseModel>();
        CourseModel model = new CourseModel();
        model.setName("English");
        model.setCourseId("101001");
        model.setTid("30001");
        modelList.add(model);
        System.out.println("对课程信息表进行插入操作："+courceInfoService.insertStudentInfo(modelList));
    }
//批量查询课程信息
    @Test
    public void selectCourseInfoTest(){
        List<String> courseIds = new ArrayList<String>();
        courseIds.add("101001");
        System.out.println("批量查询课程信息:"+courceInfoService.selectCourseInfo(courseIds));
    }
    //为某课程录入成绩
    @Test
    public void enteringScoreTest(){
        String courseId = "101001";
        List<CourseScoreModel> modelList = new ArrayList<CourseScoreModel>();
        CourseScoreModel model = new CourseScoreModel();
        model.setSid("12123214");
        model.setCourseScore(38);
        modelList.add(model);
        CourseScoreModel model1 = new CourseScoreModel();
        model1.setSid("12123215");
        model1.setCourseScore(90);
        modelList.add(model1);
        System.out.println("为课程‘"+courseId+"’录入成绩表是否成功："+courceInfoService.enteringScore(courseId,modelList));
    }
    //查询某门课程成绩排名
    @Test
    public void  SearchRankTest(){
        String courseId = "101001";
        System.out.println("课程号为：‘"+courseId+"’的成绩排名为:"+courceInfoService.SearchRank(courseId));
    }
    //查询  某门课程及格或不及格的学生
    @Test
    public void searchPass(){
        String courseId = "101001";
        Double max = 100.0;
        Double min = 60.0;
        System.out.println("课程号为：‘"+courseId+"’及格的学生为:"+courceInfoService.searchPass(courseId,max,min));
    }
    //查询  某门课程及格或不及格的人数
    @Test
    public void searchPassNumber(){
        String courseId = "101001";
        Double max = 100.0;
        Double min = 60.0;
        System.out.println("课程号为：‘"+courseId+"’及格人数为:"+courceInfoService.searchPassNumber(courseId,min,max));
    }
    //查询某个学生在本课程的排名（ZREVRANK 高到低）
    @Test
    public void searchRankOfSidTest(){
        String courseId = "101001";
        String sid = "12123211";
        System.out.println("学号为：‘"+sid+"’的学生在课程:"+courseId+"中的排名为："+courceInfoService.searchRankOfSid(courseId,sid));
    }


}
